package net.minecraft.world.inventory;

import java.util.Optional;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class NonInteractiveResultSlot extends Slot {
   public NonInteractiveResultSlot(Container p_311408_, int p_312001_, int p_311632_, int p_309399_) {
      super(p_311408_, p_312001_, p_311632_, p_309399_);
   }

   /**
    * if par2 has more items than par1, onCrafting(item,countIncrease) is called
    */
   public void onQuickCraft(ItemStack p_312884_, ItemStack p_313225_) {
   }

   /**
    * Return whether this slot's stack can be taken from this slot.
    */
   public boolean mayPickup(Player p_311019_) {
      return false;
   }

   public Optional<ItemStack> tryRemove(int p_310666_, int p_311310_, Player p_311612_) {
      return Optional.empty();
   }

   public ItemStack safeTake(int p_313087_, int p_310389_, Player p_309608_) {
      return ItemStack.EMPTY;
   }

   public ItemStack safeInsert(ItemStack p_309950_) {
      return p_309950_;
   }

   public ItemStack safeInsert(ItemStack p_311478_, int p_311938_) {
      return this.safeInsert(p_311478_);
   }

   public boolean allowModification(Player p_309707_) {
      return false;
   }

   /**
    * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
    */
   public boolean mayPlace(ItemStack p_310756_) {
      return false;
   }

   /**
    * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new stack.
    */
   public ItemStack remove(int p_310438_) {
      return ItemStack.EMPTY;
   }

   public void onTake(Player p_312646_, ItemStack p_313015_) {
   }

   public boolean isHighlightable() {
      return false;
   }

   public boolean isFake() {
      return true;
   }
}