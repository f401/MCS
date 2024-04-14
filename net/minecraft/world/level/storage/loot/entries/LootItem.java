package net.minecraft.world.level.storage.loot.entries;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

/**
 * A loot pool entry that always generates a given item.
 */
public class LootItem extends LootPoolSingletonContainer {
   public static final Codec<LootItem> CODEC = RecordCodecBuilder.create((p_297034_) -> {
      return p_297034_.group(BuiltInRegistries.ITEM.holderByNameCodec().fieldOf("name").forGetter((p_297028_) -> {
         return p_297028_.item;
      })).and(singletonFields(p_297034_)).apply(p_297034_, LootItem::new);
   });
   private final Holder<Item> item;

   private LootItem(Holder<Item> p_298860_, int p_79567_, int p_79568_, List<LootItemCondition> p_299681_, List<LootItemFunction> p_298494_) {
      super(p_79567_, p_79568_, p_299681_, p_298494_);
      this.item = p_298860_;
   }

   public LootPoolEntryType getType() {
      return LootPoolEntries.ITEM;
   }

   /**
    * Generate the loot stacks of this entry.
    * Contrary to the method name this method does not always generate one stack, it can also generate zero or multiple
    * stacks.
    */
   public void createItemStack(Consumer<ItemStack> pStackConsumer, LootContext pLootContext) {
      pStackConsumer.accept(new ItemStack(this.item));
   }

   public static LootPoolSingletonContainer.Builder<?> lootTableItem(ItemLike pItem) {
      return simpleBuilder((p_297030_, p_297031_, p_297032_, p_297033_) -> {
         return new LootItem(pItem.asItem().builtInRegistryHolder(), p_297030_, p_297031_, p_297032_, p_297033_);
      });
   }
}