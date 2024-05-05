package net.minecraft.client.multiplayer;

import com.mojang.authlib.GameProfile;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.Connection;
import net.minecraft.network.TickablePacketListener;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.configuration.ClientConfigurationPacketListener;
import net.minecraft.network.protocol.configuration.ClientboundFinishConfigurationPacket;
import net.minecraft.network.protocol.configuration.ClientboundRegistryDataPacket;
import net.minecraft.network.protocol.configuration.ClientboundUpdateEnabledFeaturesPacket;
import net.minecraft.network.protocol.configuration.ServerboundFinishConfigurationPacket;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.slf4j.Logger;

@OnlyIn(Dist.CLIENT)
public class ClientConfigurationPacketListenerImpl extends ClientCommonPacketListenerImpl implements TickablePacketListener, ClientConfigurationPacketListener {
   private static final Logger LOGGER = LogUtils.getLogger();
   private final GameProfile localGameProfile;
   private RegistryAccess.Frozen receivedRegistries;
   private FeatureFlagSet enabledFeatures;

   public ClientConfigurationPacketListenerImpl(Minecraft pMinecraft, Connection pConnection, CommonListenerCookie pCommonListenerCookie) {
      super(pMinecraft, pConnection, pCommonListenerCookie);
      this.localGameProfile = pCommonListenerCookie.localGameProfile();
      this.receivedRegistries = pCommonListenerCookie.receivedRegistries();
      this.enabledFeatures = pCommonListenerCookie.enabledFeatures();
   }

   public boolean isAcceptingMessages() {
      return this.connection.isConnected();
   }

   protected RegistryAccess.Frozen registryAccess() {
      return this.receivedRegistries;
   }

   protected void handleCustomPayload(CustomPacketPayload pPayload) {
      this.handleUnknownCustomPayload(pPayload);
   }

   private void handleUnknownCustomPayload(CustomPacketPayload pPayload) {
      LOGGER.warn("Unknown custom packet payload: {}", (Object)pPayload.id());
   }

   public void handleRegistryData(ClientboundRegistryDataPacket pPacket) {
      PacketUtils.ensureRunningOnSameThread(pPacket, this, this.minecraft);
      RegistryAccess.Frozen registryaccess$frozen = ClientRegistryLayer.createRegistryAccess().replaceFrom(ClientRegistryLayer.REMOTE, pPacket.registryHolder()).compositeAccess();
      if (!this.connection.isMemoryConnection()) {
         registryaccess$frozen.registries().forEach((p_299687_) -> {
            p_299687_.value().resetTags();
         });
      }

      this.receivedRegistries = registryaccess$frozen;
   }

   public void handleEnabledFeatures(ClientboundUpdateEnabledFeaturesPacket pPacket) {
      this.enabledFeatures = FeatureFlags.REGISTRY.fromNames(pPacket.features());
   }

   public void handleConfigurationFinished(ClientboundFinishConfigurationPacket pPacket) {
      this.connection.suspendInboundAfterProtocolChange();
      PacketUtils.ensureRunningOnSameThread(pPacket, this, this.minecraft);
      this.connection.setListener(new ClientPacketListener(this.minecraft, this.connection, new CommonListenerCookie(this.localGameProfile, this.telemetryManager, this.receivedRegistries, this.enabledFeatures, this.serverBrand, this.serverData, this.postDisconnectScreen)));
      this.connection.resumeInboundAfterProtocolChange();
      this.connection.send(new ServerboundFinishConfigurationPacket());
      net.minecraftforge.common.ForgeHooks.handleClientConfigurationComplete(this.connection);
   }

   public void tick() {
      this.sendDeferredPackets();
   }
}
