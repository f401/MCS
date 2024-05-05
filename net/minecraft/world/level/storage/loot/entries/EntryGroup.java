package net.minecraft.world.level.storage.loot.entries;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

/**
 * A composite loot pool entry container that expands all its children in order.
 * This container always succeeds.
 */
public class EntryGroup extends CompositeEntryBase {
   public static final Codec<EntryGroup> CODEC = createCodec(EntryGroup::new);

   EntryGroup(List<LootPoolEntryContainer> p_300347_, List<LootItemCondition> p_300424_) {
      super(p_300347_, p_300424_);
   }

   public LootPoolEntryType getType() {
      return LootPoolEntries.GROUP;
   }

   protected ComposableEntryContainer compose(List<? extends ComposableEntryContainer> pChildren) {
      ComposableEntryContainer composableentrycontainer2;
      switch (pChildren.size()) {
         case 0:
            composableentrycontainer2 = ALWAYS_TRUE;
            break;
         case 1:
            composableentrycontainer2 = pChildren.get(0);
            break;
         case 2:
            ComposableEntryContainer composableentrycontainer = pChildren.get(0);
            ComposableEntryContainer composableentrycontainer1 = pChildren.get(1);
            composableentrycontainer2 = (p_79556_, p_79557_) -> {
               composableentrycontainer.expand(p_79556_, p_79557_);
               composableentrycontainer1.expand(p_79556_, p_79557_);
               return true;
            };
            break;
         default:
            composableentrycontainer2 = (p_297026_, p_297027_) -> {
               for(ComposableEntryContainer composableentrycontainer3 : pChildren) {
                  composableentrycontainer3.expand(p_297026_, p_297027_);
               }

               return true;
            };
      }

      return composableentrycontainer2;
   }

   public static EntryGroup.Builder list(LootPoolEntryContainer.Builder<?>... pChildren) {
      return new EntryGroup.Builder(pChildren);
   }

   public static class Builder extends LootPoolEntryContainer.Builder<EntryGroup.Builder> {
      private final ImmutableList.Builder<LootPoolEntryContainer> entries = ImmutableList.builder();

      public Builder(LootPoolEntryContainer.Builder<?>... pChildren) {
         for(LootPoolEntryContainer.Builder<?> builder : pChildren) {
            this.entries.add(builder.build());
         }

      }

      protected EntryGroup.Builder getThis() {
         return this;
      }

      public EntryGroup.Builder append(LootPoolEntryContainer.Builder<?> pChildBuilder) {
         this.entries.add(pChildBuilder.build());
         return this;
      }

      public LootPoolEntryContainer build() {
         return new EntryGroup(this.entries.build(), this.getConditions());
      }
   }
}