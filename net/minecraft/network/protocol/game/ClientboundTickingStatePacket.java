package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.TickRateManager;

public record ClientboundTickingStatePacket(float tickRate, boolean isFrozen) implements Packet<ClientGamePacketListener> {
   public ClientboundTickingStatePacket(FriendlyByteBuf pBuffer) {
      this(pBuffer.readFloat(), pBuffer.readBoolean());
   }

   public static ClientboundTickingStatePacket from(TickRateManager pTickRateManager) {
      return new ClientboundTickingStatePacket(pTickRateManager.tickrate(), pTickRateManager.isFrozen());
   }

   /**
    * Writes the raw packet data to the data stream.
    */
   public void write(FriendlyByteBuf pBuffer) {
      pBuffer.writeFloat(this.tickRate);
      pBuffer.writeBoolean(this.isFrozen);
   }

   /**
    * Passes this Packet on to the NetHandler for processing.
    */
   public void handle(ClientGamePacketListener pHandler) {
      pHandler.handleTickingState(this);
   }
}