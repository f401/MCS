package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public record ClientboundResetScorePacket(String owner, @Nullable String objectiveName) implements Packet<ClientGamePacketListener> {
   public ClientboundResetScorePacket(FriendlyByteBuf pBuffer) {
      this(pBuffer.readUtf(), pBuffer.readNullable(FriendlyByteBuf::readUtf));
   }

   /**
    * Writes the raw packet data to the data stream.
    */
   public void write(FriendlyByteBuf pBuffer) {
      pBuffer.writeUtf(this.owner);
      pBuffer.writeNullable(this.objectiveName, FriendlyByteBuf::writeUtf);
   }

   /**
    * Passes this Packet on to the NetHandler for processing.
    */
   public void handle(ClientGamePacketListener pHandler) {
      pHandler.handleResetScore(this);
   }
}