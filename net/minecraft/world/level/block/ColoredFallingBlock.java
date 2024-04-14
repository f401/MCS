package net.minecraft.world.level.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class ColoredFallingBlock extends FallingBlock {
   public static final MapCodec<ColoredFallingBlock> CODEC = RecordCodecBuilder.mapCodec((p_311859_) -> {
      return p_311859_.group(ColorRGBA.CODEC.fieldOf("falling_dust_color").forGetter((p_309656_) -> {
         return p_309656_.dustColor;
      }), propertiesCodec()).apply(p_311859_, ColoredFallingBlock::new);
   });
   private final ColorRGBA dustColor;

   public MapCodec<ColoredFallingBlock> codec() {
      return CODEC;
   }

   public ColoredFallingBlock(ColorRGBA p_310631_, BlockBehaviour.Properties p_312848_) {
      super(p_312848_);
      this.dustColor = p_310631_;
   }

   public int getDustColor(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
      return this.dustColor.rgba();
   }
}