package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CalibratedSculkSensorBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;

public class CalibratedSculkSensorBlock extends SculkSensorBlock {
   public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

   public CalibratedSculkSensorBlock(BlockBehaviour.Properties p_277532_) {
      super(p_277532_);
      this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos p_277925_, BlockState p_277938_) {
      return new CalibratedSculkSensorBlockEntity(p_277925_, p_277938_);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_277645_, BlockState p_278033_, BlockEntityType<T> p_277641_) {
      return !p_277645_.isClientSide ? createTickerHelper(p_277641_, BlockEntityType.CALIBRATED_SCULK_SENSOR, (p_296934_, p_296935_, p_296936_, p_296937_) -> {
         VibrationSystem.Ticker.tick(p_296934_, p_296937_.getVibrationData(), p_296937_.getVibrationUser());
      }) : null;
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext p_277423_) {
      return super.getStateForPlacement(p_277423_).setValue(FACING, p_277423_.getHorizontalDirection());
   }

   /**
    * Returns the signal this block emits in the given direction.
    * 
    * <p>
    * NOTE: directions in redstone signal related methods are backwards, so this method
    * checks for the signal emitted in the <i>opposite</i> direction of the one given.
    * 
    * @deprecated call via {@link net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase#getSignal}
    * whenever possible. Implementing/overriding is fine.
    */
   public int getSignal(BlockState p_277782_, BlockGetter p_277556_, BlockPos p_277903_, Direction p_278059_) {
      return p_278059_ != p_277782_.getValue(FACING) ? super.getSignal(p_277782_, p_277556_, p_277903_, p_278059_) : 0;
   }

   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_277652_) {
      super.createBlockStateDefinition(p_277652_);
      p_277652_.add(FACING);
   }

   /**
    * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
    * blockstate.
    * @deprecated call via {@link net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase#rotate} whenever
    * possible. Implementing/overriding is fine.
    */
   public BlockState rotate(BlockState p_277545_, Rotation p_277482_) {
      return p_277545_.setValue(FACING, p_277482_.rotate(p_277545_.getValue(FACING)));
   }

   /**
    * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
    * blockstate.
    * @deprecated call via {@link net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase#mirror} whenever
    * possible. Implementing/overriding is fine.
    */
   public BlockState mirror(BlockState p_277615_, Mirror p_277916_) {
      return p_277615_.rotate(p_277916_.getRotation(p_277615_.getValue(FACING)));
   }

   public int getActiveTicks() {
      return 10;
   }
}