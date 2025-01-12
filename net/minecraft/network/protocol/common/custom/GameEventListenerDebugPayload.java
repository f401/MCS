package net.minecraft.network.protocol.common.custom;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.PositionSourceType;

public record GameEventListenerDebugPayload(PositionSource listenerPos, int listenerRange) implements CustomPacketPayload {
   public static final ResourceLocation ID = new ResourceLocation("debug/game_event_listeners");

   public GameEventListenerDebugPayload(FriendlyByteBuf pBuffer) {
      this(PositionSourceType.fromNetwork(pBuffer), pBuffer.readVarInt());
   }

   public void write(FriendlyByteBuf pBuffer) {
      PositionSourceType.toNetwork(this.listenerPos, pBuffer);
      pBuffer.writeVarInt(this.listenerRange);
   }

   public ResourceLocation id() {
      return ID;
   }
}