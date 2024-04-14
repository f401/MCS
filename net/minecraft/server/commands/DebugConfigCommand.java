package net.minecraft.server.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.UuidArgument;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl;

public class DebugConfigCommand {
   public static void register(CommandDispatcher<CommandSourceStack> pDispatcher) {
      pDispatcher.register(Commands.literal("debugconfig").requires((p_299396_) -> {
         return p_299396_.hasPermission(3);
      }).then(Commands.literal("config").then(Commands.argument("target", EntityArgument.player()).executes((p_300433_) -> {
         return config(p_300433_.getSource(), EntityArgument.getPlayer(p_300433_, "target"));
      }))).then(Commands.literal("unconfig").then(Commands.argument("target", UuidArgument.uuid()).suggests((p_297904_, p_297883_) -> {
         return SharedSuggestionProvider.suggest(getUuidsInConfig(p_297904_.getSource().getServer()), p_297883_);
      }).executes((p_301004_) -> {
         return unconfig(p_301004_.getSource(), UuidArgument.getUuid(p_301004_, "target"));
      }))));
   }

   private static Iterable<String> getUuidsInConfig(MinecraftServer pServer) {
      Set<String> set = new HashSet<>();

      for(Connection connection : pServer.getConnection().getConnections()) {
         PacketListener packetlistener = connection.getPacketListener();
         if (packetlistener instanceof ServerConfigurationPacketListenerImpl serverconfigurationpacketlistenerimpl) {
            set.add(serverconfigurationpacketlistenerimpl.getOwner().getId().toString());
         }
      }

      return set;
   }

   private static int config(CommandSourceStack pSource, ServerPlayer pTarget) {
      GameProfile gameprofile = pTarget.getGameProfile();
      pTarget.connection.switchToConfig();
      pSource.sendSuccess(() -> {
         return Component.literal("Switched player " + gameprofile.getName() + "(" + gameprofile.getId() + ") to config mode");
      }, false);
      return 1;
   }

   private static int unconfig(CommandSourceStack pSource, UUID pTarget) {
      for(Connection connection : pSource.getServer().getConnection().getConnections()) {
         PacketListener packetlistener = connection.getPacketListener();
         if (packetlistener instanceof ServerConfigurationPacketListenerImpl serverconfigurationpacketlistenerimpl) {
            if (serverconfigurationpacketlistenerimpl.getOwner().getId().equals(pTarget)) {
               serverconfigurationpacketlistenerimpl.returnToWorld();
            }
         }
      }

      pSource.sendFailure(Component.literal("Can't find player to unconfig"));
      return 0;
   }
}