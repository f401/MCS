package net.minecraft.network.chat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;

public class ClickEvent {
   public static final Codec<ClickEvent> CODEC = RecordCodecBuilder.create((p_311166_) -> {
      return p_311166_.group(ClickEvent.Action.CODEC.forGetter((p_313238_) -> {
         return p_313238_.action;
      }), Codec.STRING.fieldOf("value").forGetter((p_312346_) -> {
         return p_312346_.value;
      })).apply(p_311166_, ClickEvent::new);
   });
   private final ClickEvent.Action action;
   private final String value;

   public ClickEvent(ClickEvent.Action p_130620_, String p_130621_) {
      this.action = p_130620_;
      this.value = p_130621_;
   }

   /**
    * Gets the action to perform when this event is raised.
    */
   public ClickEvent.Action getAction() {
      return this.action;
   }

   /**
    * Gets the value to perform the action on when this event is raised.  For example, if the action is "open URL", this
    * would be the URL to open.
    */
   public String getValue() {
      return this.value;
   }

   public boolean equals(Object pOther) {
      if (this == pOther) {
         return true;
      } else if (pOther != null && this.getClass() == pOther.getClass()) {
         ClickEvent clickevent = (ClickEvent)pOther;
         return this.action == clickevent.action && this.value.equals(clickevent.value);
      } else {
         return false;
      }
   }

   public String toString() {
      return "ClickEvent{action=" + this.action + ", value='" + this.value + "'}";
   }

   public int hashCode() {
      int i = this.action.hashCode();
      return 31 * i + this.value.hashCode();
   }

   public static enum Action implements StringRepresentable {
      OPEN_URL("open_url", true),
      OPEN_FILE("open_file", false),
      RUN_COMMAND("run_command", true),
      SUGGEST_COMMAND("suggest_command", true),
      CHANGE_PAGE("change_page", true),
      COPY_TO_CLIPBOARD("copy_to_clipboard", true);

      public static final MapCodec<ClickEvent.Action> UNSAFE_CODEC = StringRepresentable.fromEnum(ClickEvent.Action::values).fieldOf("action");
      public static final MapCodec<ClickEvent.Action> CODEC = ExtraCodecs.validate(UNSAFE_CODEC, ClickEvent.Action::filterForSerialization);
      private final boolean allowFromServer;
      /** The canonical name used to refer to this action. */
      private final String name;

      private Action(String pName, boolean pAllowFromServer) {
         this.name = pName;
         this.allowFromServer = pAllowFromServer;
      }

      /**
       * Indicates whether this event can be run from chat text.
       */
      public boolean isAllowedFromServer() {
         return this.allowFromServer;
      }

      public String getSerializedName() {
         return this.name;
      }

      public static DataResult<ClickEvent.Action> filterForSerialization(ClickEvent.Action p_311653_) {
         return !p_311653_.isAllowedFromServer() ? DataResult.error(() -> {
            return "Action not allowed: " + p_311653_;
         }) : DataResult.success(p_311653_, Lifecycle.stable());
      }
   }
}