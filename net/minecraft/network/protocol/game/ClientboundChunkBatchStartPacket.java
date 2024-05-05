package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public record ClientboundChunkBatchStartPacket() implements Packet<ClientGamePacketListener> {
   public ClientboundChunkBatchStartPacket(FriendlyByteBuf pBuffer) {
      this();
   }

   /**
    * Writes the raw packet data to the data stream.
    */
   public void write(FriendlyByteBuf pBuffer) {
   }

   /**
    * Passes this Packet on to the NetHandler for processing.
    */
   public void handle(ClientGamePacketListener pHandler) {
      pHandler.handleChunkBatchStart(this);
   }
}