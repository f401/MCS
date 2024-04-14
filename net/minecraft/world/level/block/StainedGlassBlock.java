package net.minecraft.world.level.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class StainedGlassBlock extends TransparentBlock implements BeaconBeamBlock {
   public static final MapCodec<StainedGlassBlock> CODEC = RecordCodecBuilder.mapCodec((p_312506_) -> {
      return p_312506_.group(DyeColor.CODEC.fieldOf("color").forGetter(StainedGlassBlock::getColor), propertiesCodec()).apply(p_312506_, StainedGlassBlock::new);
   });
   private final DyeColor color;

   public MapCodec<StainedGlassBlock> codec() {
      return CODEC;
   }

   public StainedGlassBlock(DyeColor p_56833_, BlockBehaviour.Properties p_56834_) {
      super(p_56834_);
      this.color = p_56833_;
   }

   public DyeColor getColor() {
      return this.color;
   }
}