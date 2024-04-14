package net.minecraft.world.level.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class WeatheringCopperSlabBlock extends SlabBlock implements WeatheringCopper {
   public static final MapCodec<WeatheringCopperSlabBlock> CODEC = RecordCodecBuilder.mapCodec((p_311462_) -> {
      return p_311462_.group(WeatheringCopper.WeatherState.CODEC.fieldOf("weathering_state").forGetter(ChangeOverTimeBlock::getAge), propertiesCodec()).apply(p_311462_, WeatheringCopperSlabBlock::new);
   });
   private final WeatheringCopper.WeatherState weatherState;

   public MapCodec<WeatheringCopperSlabBlock> codec() {
      return CODEC;
   }

   public WeatheringCopperSlabBlock(WeatheringCopper.WeatherState p_154938_, BlockBehaviour.Properties p_154939_) {
      super(p_154939_);
      this.weatherState = p_154938_;
   }

   /**
    * Performs a random tick on a block.
    */
   public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
      this.changeOverTime(pState, pLevel, pPos, pRandom);
   }

   /**
    * @return whether this block needs random ticking.
    */
   public boolean isRandomlyTicking(BlockState pState) {
      return WeatheringCopper.getNext(pState.getBlock()).isPresent();
   }

   public WeatheringCopper.WeatherState getAge() {
      return this.weatherState;
   }
}