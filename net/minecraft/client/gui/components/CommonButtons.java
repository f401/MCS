package net.minecraft.client.gui.components;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CommonButtons {
   public static SpriteIconButton language(int pWidth, Button.OnPress pOnPress, boolean pIconOnly) {
      return SpriteIconButton.builder(Component.translatable("options.language"), pOnPress, pIconOnly).width(pWidth).sprite(new ResourceLocation("icon/language"), 15, 15).build();
   }

   public static SpriteIconButton accessibility(int pWidth, Button.OnPress pOnPress, boolean pIconOnly) {
      return SpriteIconButton.builder(Component.translatable("options.accessibility"), pOnPress, pIconOnly).width(pWidth).sprite(new ResourceLocation("icon/accessibility"), 15, 15).build();
   }
}