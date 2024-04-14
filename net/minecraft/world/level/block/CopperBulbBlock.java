package net.minecraft.world.level.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class CopperBulbBlock extends Block {
   public static final MapCodec<CopperBulbBlock> CODEC = simpleCodec(CopperBulbBlock::new);
   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
   public static final BooleanProperty LIT = BlockStateProperties.LIT;

   protected MapCodec<? extends CopperBulbBlock> codec() {
      return CODEC;
   }

   public CopperBulbBlock(BlockBehaviour.Properties p_311115_) {
      super(p_311115_);
      this.registerDefaultState(this.defaultBlockState().setValue(LIT, Boolean.valueOf(false)).setValue(POWERED, Boolean.valueOf(false)));
   }

   public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
      if (pOldState.getBlock() != pState.getBlock() && pLevel instanceof ServerLevel serverlevel) {
         this.checkAndFlip(pState, serverlevel, pPos);
      }

   }

   public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pNeighborBlock, BlockPos pNeighborPos, boolean pMovedByPiston) {
      if (pLevel instanceof ServerLevel serverlevel) {
         this.checkAndFlip(pState, serverlevel, pPos);
      }

   }

   public void checkAndFlip(BlockState pState, ServerLevel pLevel, BlockPos pPos) {
      boolean flag = pLevel.hasNeighborSignal(pPos);
      if (flag != pState.getValue(POWERED)) {
         BlockState blockstate = pState;
         if (!pState.getValue(POWERED)) {
            blockstate = pState.cycle(LIT);
            pLevel.playSound((Player)null, pPos, blockstate.getValue(LIT) ? SoundEvents.COPPER_BULB_TURN_ON : SoundEvents.COPPER_BULB_TURN_OFF, SoundSource.BLOCKS);
         }

         pLevel.setBlock(pPos, blockstate.setValue(POWERED, Boolean.valueOf(flag)), 3);
      }
   }

   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
      pBuilder.add(LIT, POWERED);
   }

   /**
    * @deprecated call via {@link
    * net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase#hasAnalogOutputSignal} whenever possible.
    * Implementing/overriding is fine.
    */
   public boolean hasAnalogOutputSignal(BlockState pState) {
      return true;
   }

   /**
    * Returns the analog signal this block emits. This is the signal a comparator can read from it.
    * 
    * @deprecated call via {@link
    * net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase#getAnalogOutputSignal} whenever possible.
    * Implementing/overriding is fine.
    */
   public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
      return pLevel.getBlockState(pPos).getValue(LIT) ? 15 : 0;
   }
}