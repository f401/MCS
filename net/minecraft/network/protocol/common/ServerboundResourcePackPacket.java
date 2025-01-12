package net.minecraft.network.protocol.common;

import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public record ServerboundResourcePackPacket(UUID id, ServerboundResourcePackPacket.Action action) implements Packet<ServerCommonPacketListener> {
   public ServerboundResourcePackPacket(FriendlyByteBuf pBuffer) {
      this(pBuffer.readUUID(), pBuffer.readEnum(ServerboundResourcePackPacket.Action.class));
   }

   /**
    * Writes the raw packet data to the data stream.
    */
   public void write(FriendlyByteBuf pBuffer) {
      pBuffer.writeUUID(this.id);
      pBuffer.writeEnum(this.action);
   }

   /**
    * Passes this Packet on to the NetHandler for processing.
    */
   public void handle(ServerCommonPacketListener pHandler) {
      pHandler.handleResourcePackResponse(this);
   }

   public static enum Action {
      SUCCESSFULLY_LOADED,
      DECLINED,
      FAILED_DOWNLOAD,
      ACCEPTED,
      DOWNLOADED,
      INVALID_URL,
      FAILED_RELOAD,
      DISCARDED;

      public boolean isTerminal() {
         return this != ACCEPTED && this != DOWNLOADED;
      }
   }
}