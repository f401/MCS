package net.minecraft.network.protocol.common;

import java.util.Optional;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public record ClientboundResourcePackPopPacket(Optional<UUID> id) implements Packet<ClientCommonPacketListener> {
   public ClientboundResourcePackPopPacket(FriendlyByteBuf pBuffer) {
      this(pBuffer.readOptional(FriendlyByteBuf::readUUID));
   }

   /**
    * Writes the raw packet data to the data stream.
    */
   public void write(FriendlyByteBuf pBuffer) {
      pBuffer.writeOptional(this.id, FriendlyByteBuf::writeUUID);
   }

   /**
    * Passes this Packet on to the NetHandler for processing.
    */
   public void handle(ClientCommonPacketListener pHandler) {
      pHandler.handleResourcePackPop(this);
   }
}