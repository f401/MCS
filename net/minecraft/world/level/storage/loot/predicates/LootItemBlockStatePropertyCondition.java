package net.minecraft.world.level.storage.loot.predicates;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.Set;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public record LootItemBlockStatePropertyCondition(Holder<Block> block, Optional<StatePropertiesPredicate> properties) implements LootItemCondition {
   public static final Codec<LootItemBlockStatePropertyCondition> CODEC = ExtraCodecs.validate(RecordCodecBuilder.create((p_300019_) -> {
      return p_300019_.group(BuiltInRegistries.BLOCK.holderByNameCodec().fieldOf("block").forGetter(LootItemBlockStatePropertyCondition::block), ExtraCodecs.strictOptionalField(StatePropertiesPredicate.CODEC, "properties").forGetter(LootItemBlockStatePropertyCondition::properties)).apply(p_300019_, LootItemBlockStatePropertyCondition::new);
   }), LootItemBlockStatePropertyCondition::validate);

   private static DataResult<LootItemBlockStatePropertyCondition> validate(LootItemBlockStatePropertyCondition p_298673_) {
      return p_298673_.properties().flatMap((p_297356_) -> {
         return p_297356_.checkState(p_298673_.block().value().getStateDefinition());
      }).map((p_298572_) -> {
         return DataResult.<LootItemBlockStatePropertyCondition>error(() -> {
            return "Block " + p_298673_.block() + " has no property" + p_298572_;
         });
      }).orElse(DataResult.success(p_298673_));
   }

   public LootItemConditionType getType() {
      return LootItemConditions.BLOCK_STATE_PROPERTY;
   }

   /**
    * Get the parameters used by this object.
    */
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return Set.of(LootContextParams.BLOCK_STATE);
   }

   public boolean test(LootContext pContext) {
      BlockState blockstate = pContext.getParamOrNull(LootContextParams.BLOCK_STATE);
      return blockstate != null && blockstate.is(this.block) && (this.properties.isEmpty() || this.properties.get().matches(blockstate));
   }

   public static LootItemBlockStatePropertyCondition.Builder hasBlockStateProperties(Block pBlock) {
      return new LootItemBlockStatePropertyCondition.Builder(pBlock);
   }

   public static class Builder implements LootItemCondition.Builder {
      private final Holder<Block> block;
      private Optional<StatePropertiesPredicate> properties = Optional.empty();

      public Builder(Block pBlock) {
         this.block = pBlock.builtInRegistryHolder();
      }

      public LootItemBlockStatePropertyCondition.Builder setProperties(StatePropertiesPredicate.Builder pStatePredicateBuilder) {
         this.properties = pStatePredicateBuilder.build();
         return this;
      }

      public LootItemCondition build() {
         return new LootItemBlockStatePropertyCondition(this.block, this.properties);
      }
   }
}