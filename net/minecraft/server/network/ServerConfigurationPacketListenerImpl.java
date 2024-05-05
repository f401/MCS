package net.minecraft.server.network;

import com.mojang.authlib.GameProfile;
import com.mojang.logging.LogUtils;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.annotation.Nullable;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySynchronization;
import net.minecraft.network.Connection;
import net.minecraft.network.TickablePacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.ClientboundDisconnectPacket;
import net.minecraft.network.protocol.common.ClientboundUpdateTagsPacket;
import net.minecraft.network.protocol.common.ServerboundClientInformationPacket;
import net.minecraft.network.protocol.common.ServerboundResourcePackPacket;
import net.minecraft.network.protocol.common.custom.BrandPayload;
import net.minecraft.network.protocol.configuration.ClientboundRegistryDataPacket;
import net.minecraft.network.protocol.configuration.ClientboundUpdateEnabledFeaturesPacket;
import net.minecraft.network.protocol.configuration.ServerConfigurationPacketListener;
import net.minecraft.network.protocol.configuration.ServerboundFinishConfigurationPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.config.JoinWorldTask;
import net.minecraft.server.network.config.ServerResourcePackConfigurationTask;
import net.minecraft.server.players.PlayerList;
import net.minecraft.tags.TagNetworkSerialization;
import net.minecraft.world.flag.FeatureFlags;
import org.slf4j.Logger;

public class ServerConfigurationPacketListenerImpl extends ServerCommonPacketListenerImpl implements TickablePacketListener, ServerConfigurationPacketListener {
   private static final Logger LOGGER = LogUtils.getLogger();
   private static final Component DISCONNECT_REASON_INVALID_DATA = Component.translatable("multiplayer.disconnect.invalid_player_data");
   private static final ConfigurationTask.Type VANILLA_START = new ConfigurationTask.Type("vanilla_start");
   private final GameProfile gameProfile;
   private final Queue<ConfigurationTask> configurationTasks = new ConcurrentLinkedQueue<>();
   @Nullable
   private ConfigurationTask currentTask;
   private ClientInformation clientInformation;
   private final net.minecraftforge.network.config.ConfigurationTaskContext taskContext;

   public ServerConfigurationPacketListenerImpl(MinecraftServer pServer, Connection pConnection, CommonListenerCookie pCookie) {
      super(pServer, pConnection, pCookie);
      this.gameProfile = pCookie.gameProfile();
      this.clientInformation = pCookie.clientInformation();
      this.taskContext = new net.minecraftforge.network.config.ConfigurationTaskContext(pConnection, this::send, this::finishCurrentTask);
   }

   protected GameProfile playerProfile() {
      return this.gameProfile;
   }

   /**
    * Invoked when disconnecting, the parameter is a ChatComponent describing the reason for termination
    */
   public void onDisconnect(Component pReason) {
      LOGGER.info("{} lost connection: {}", this.gameProfile, pReason.getString());
      super.onDisconnect(pReason);
   }

   public boolean isAcceptingMessages() {
      return this.connection.isConnected();
   }

   private void vanillaStart() {
      this.send(new ClientboundCustomPayloadPacket(new BrandPayload(this.server.getServerModName())));
      LayeredRegistryAccess<RegistryLayer> layeredregistryaccess = this.server.registries();
      this.send(new ClientboundUpdateEnabledFeaturesPacket(FeatureFlags.REGISTRY.toNames(this.server.getWorldData().enabledFeatures())));
      this.send(new ClientboundRegistryDataPacket((new RegistryAccess.ImmutableRegistryAccess(RegistrySynchronization.networkedRegistries(layeredregistryaccess))).freeze()));
      this.send(new ClientboundUpdateTagsPacket(TagNetworkSerialization.serializeTagsToNetwork(layeredregistryaccess)));
   }

   public void startConfiguration() {
      net.minecraftforge.event.ForgeEventFactory.gatherLoginConfigTasks(this.connection, this.configurationTasks::add);
      this.configurationTasks.add(new net.minecraftforge.network.config.SimpleConfigurationTask(VANILLA_START, this::vanillaStart));
      this.addOptionalTasks();
      this.configurationTasks.add(new JoinWorldTask());
      this.startNextTask();
   }

   public void returnToWorld() {
      this.configurationTasks.add(new JoinWorldTask());
      this.startNextTask();
   }

   private void addOptionalTasks() {
      this.server.getServerResourcePack().ifPresent((p_300306_) -> {
         this.configurationTasks.add(new ServerResourcePackConfigurationTask(p_300306_));
      });
   }

   public void handleClientInformation(ServerboundClientInformationPacket pPacket) {
      this.clientInformation = pPacket.information();
   }

   public void handleResourcePackResponse(ServerboundResourcePackPacket pPacket) {
      super.handleResourcePackResponse(pPacket);
      if (pPacket.getAction() != ServerboundResourcePackPacket.Action.ACCEPTED) {
         this.finishCurrentTask(ServerResourcePackConfigurationTask.TYPE);
      }

   }

   public void handleConfigurationFinished(ServerboundFinishConfigurationPacket pPacket) {
      this.connection.suspendInboundAfterProtocolChange();
      PacketUtils.ensureRunningOnSameThread(pPacket, this, this.server);
      this.finishCurrentTask(JoinWorldTask.TYPE);

      try {
         PlayerList playerlist = this.server.getPlayerList();
         if (playerlist.getPlayer(this.gameProfile.getId()) != null) {
            this.disconnect(PlayerList.DUPLICATE_LOGIN_DISCONNECT_MESSAGE);
            return;
         }

         Component component = playerlist.canPlayerLogin(this.connection.getRemoteAddress(), this.gameProfile);
         if (component != null) {
            this.disconnect(component);
            return;
         }

         ServerPlayer serverplayer = playerlist.getPlayerForLogin(this.gameProfile, this.clientInformation);
         playerlist.placeNewPlayer(this.connection, serverplayer, this.createCookie(this.clientInformation));
         this.connection.resumeInboundAfterProtocolChange();
      } catch (Exception exception) {
         LOGGER.error("Couldn't place player in world", (Throwable)exception);
         this.connection.send(new ClientboundDisconnectPacket(DISCONNECT_REASON_INVALID_DATA));
         this.connection.disconnect(DISCONNECT_REASON_INVALID_DATA);
      }

   }

   public void tick() {
      this.keepConnectionAlive();
   }

   private void startNextTask() {
      if (this.currentTask != null) {
         throw new IllegalStateException("Task " + this.currentTask.type().id() + " has not finished yet");
      } else if (this.isAcceptingMessages()) {
         ConfigurationTask configurationtask = this.configurationTasks.poll();
         if (configurationtask != null) {
            this.currentTask = configurationtask;
            configurationtask.start(this.taskContext);
         }

      }
   }

   public void finishCurrentTask(ConfigurationTask.Type pTaskType) {
      ConfigurationTask.Type configurationtask$type = this.currentTask != null ? this.currentTask.type() : null;
      if (!pTaskType.equals(configurationtask$type)) {
         throw new IllegalStateException("Unexpected request for task finish, current task: " + configurationtask$type + ", requested: " + pTaskType);
      } else {
         this.currentTask = null;
         this.startNextTask();
      }
   }
}
