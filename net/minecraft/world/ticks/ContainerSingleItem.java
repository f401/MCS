package net.minecraft.world.ticks;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface ContainerSingleItem extends Container {
   ItemStack getTheItem();

   ItemStack splitTheItem(int pAmount);

   void setTheItem(ItemStack pItem);

   BlockEntity getContainerBlockEntity();

   default ItemStack removeTheItem() {
      return this.splitTheItem(this.getMaxStackSize());
   }

   /**
    * Returns the number of slots in the inventory.
    */
   default int getContainerSize() {
      return 1;
   }

   default boolean isEmpty() {
      return this.getTheItem().isEmpty();
   }

   default void clearContent() {
      this.removeTheItem();
   }

   /**
    * Removes a stack from the given slot and returns it.
    */
   default ItemStack removeItemNoUpdate(int pSlot) {
      return this.removeItem(pSlot, this.getMaxStackSize());
   }

   /**
    * Returns the stack in the given slot.
    */
   default ItemStack getItem(int pSlot) {
      return pSlot == 0 ? this.getTheItem() : ItemStack.EMPTY;
   }

   /**
    * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
    */
   default ItemStack removeItem(int pSlot, int pAmount) {
      return pSlot != 0 ? ItemStack.EMPTY : this.splitTheItem(pAmount);
   }

   /**
    * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
    */
   default void setItem(int pSlot, ItemStack pStack) {
      if (pSlot == 0) {
         this.setTheItem(pStack);
      }

   }

   /**
    * Don't rename this method to canInteractWith due to conflicts with Container
    */
   default boolean stillValid(Player pPlayer) {
      return Container.stillValidBlockEntity(this.getContainerBlockEntity(), pPlayer);
   }
}