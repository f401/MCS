package net.minecraft.world.level.storage.loot.providers.number;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Set;
import net.minecraft.util.Mth;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;

public record UniformGenerator(NumberProvider min, NumberProvider max) implements NumberProvider {
   public static final Codec<UniformGenerator> CODEC = RecordCodecBuilder.create((p_299644_) -> {
      return p_299644_.group(NumberProviders.CODEC.fieldOf("min").forGetter(UniformGenerator::min), NumberProviders.CODEC.fieldOf("max").forGetter(UniformGenerator::max)).apply(p_299644_, UniformGenerator::new);
   });

   public LootNumberProviderType getType() {
      return NumberProviders.UNIFORM;
   }

   public static UniformGenerator between(float pMin, float pMax) {
      return new UniformGenerator(ConstantValue.exactly(pMin), ConstantValue.exactly(pMax));
   }

   public int getInt(LootContext pLootContext) {
      return Mth.nextInt(pLootContext.getRandom(), this.min.getInt(pLootContext), this.max.getInt(pLootContext));
   }

   public float getFloat(LootContext pLootContext) {
      return Mth.nextFloat(pLootContext.getRandom(), this.min.getFloat(pLootContext), this.max.getFloat(pLootContext));
   }

   /**
    * Get the parameters used by this object.
    */
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return Sets.union(this.min.getReferencedContextParams(), this.max.getReferencedContextParams());
   }
}