package net.minecraft.server.network.config;

import java.util.function.Consumer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundResourcePackPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ConfigurationTask;

public class ServerResourcePackConfigurationTask implements ConfigurationTask {
   public static final ConfigurationTask.Type TYPE = new ConfigurationTask.Type("server_resource_pack");
   private final MinecraftServer.ServerResourcePackInfo info;

   public ServerResourcePackConfigurationTask(MinecraftServer.ServerResourcePackInfo pInfo) {
      this.info = pInfo;
   }

   public void start(Consumer<Packet<?>> pTask) {
      pTask.accept(new ClientboundResourcePackPacket(this.info.url(), this.info.hash(), this.info.isRequired(), this.info.prompt()));
   }

   public ConfigurationTask.Type type() {
      return TYPE;
   }
}