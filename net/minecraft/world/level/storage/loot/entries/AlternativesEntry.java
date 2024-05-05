package net.minecraft.world.level.storage.loot.entries;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

/**
 * A composite loot pool entry container that expands all its children in order until one of them succeeds.
 * This container succeeds if one of its children succeeds.
 */
public class AlternativesEntry extends CompositeEntryBase {
   public static final Codec<AlternativesEntry> CODEC = createCodec(AlternativesEntry::new);

   AlternativesEntry(List<LootPoolEntryContainer> p_299703_, List<LootItemCondition> p_299222_) {
      super(p_299703_, p_299222_);
   }

   public LootPoolEntryType getType() {
      return LootPoolEntries.ALTERNATIVES;
   }

   protected ComposableEntryContainer compose(List<? extends ComposableEntryContainer> pChildren) {
      ComposableEntryContainer composableentrycontainer;
      switch (pChildren.size()) {
         case 0:
            composableentrycontainer = ALWAYS_FALSE;
            break;
         case 1:
            composableentrycontainer = pChildren.get(0);
            break;
         case 2:
            composableentrycontainer = pChildren.get(0).or(pChildren.get(1));
            break;
         default:
            composableentrycontainer = (p_297016_, p_297017_) -> {
               for(ComposableEntryContainer composableentrycontainer1 : pChildren) {
                  if (composableentrycontainer1.expand(p_297016_, p_297017_)) {
                     return true;
                  }
               }

               return false;
            };
      }

      return composableentrycontainer;
   }

   public void validate(ValidationContext pValidationContext) {
      super.validate(pValidationContext);

      for(int i = 0; i < this.children.size() - 1; ++i) {
         if ((this.children.get(i)).conditions.isEmpty()) {
            pValidationContext.reportProblem("Unreachable entry!");
         }
      }

   }

   public static AlternativesEntry.Builder alternatives(LootPoolEntryContainer.Builder<?>... pChildren) {
      return new AlternativesEntry.Builder(pChildren);
   }

   public static <E> AlternativesEntry.Builder alternatives(Collection<E> pChildrenSources, Function<E, LootPoolEntryContainer.Builder<?>> pToChildrenFunction) {
      return new AlternativesEntry.Builder(pChildrenSources.stream().map(pToChildrenFunction::apply).toArray((p_230932_) -> {
         return new LootPoolEntryContainer.Builder[p_230932_];
      }));
   }

   public static class Builder extends LootPoolEntryContainer.Builder<AlternativesEntry.Builder> {
      private final ImmutableList.Builder<LootPoolEntryContainer> entries = ImmutableList.builder();

      public Builder(LootPoolEntryContainer.Builder<?>... pChildren) {
         for(LootPoolEntryContainer.Builder<?> builder : pChildren) {
            this.entries.add(builder.build());
         }

      }

      protected AlternativesEntry.Builder getThis() {
         return this;
      }

      public AlternativesEntry.Builder otherwise(LootPoolEntryContainer.Builder<?> pChildBuilder) {
         this.entries.add(pChildBuilder.build());
         return this;
      }

      public LootPoolEntryContainer build() {
         return new AlternativesEntry(this.entries.build(), this.getConditions());
      }
   }
}