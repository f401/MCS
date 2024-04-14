package net.minecraft.world.level.storage.loot.functions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

/**
 * LootItemFunction that reduces a stack's count based on the {@linkplain LootContextParams#EXPLOSION_RADIUS explosion
 * radius}.
 */
public class ApplyExplosionDecay extends LootItemConditionalFunction {
   public static final Codec<ApplyExplosionDecay> CODEC = RecordCodecBuilder.create((p_297802_) -> {
      return commonFields(p_297802_).apply(p_297802_, ApplyExplosionDecay::new);
   });

   private ApplyExplosionDecay(List<LootItemCondition> p_301217_) {
      super(p_301217_);
   }

   public LootItemFunctionType getType() {
      return LootItemFunctions.EXPLOSION_DECAY;
   }

   /**
    * Called to perform the actual action of this function, after conditions have been checked.
    */
   public ItemStack run(ItemStack pStack, LootContext pContext) {
      Float f = pContext.getParamOrNull(LootContextParams.EXPLOSION_RADIUS);
      if (f != null) {
         RandomSource randomsource = pContext.getRandom();
         float f1 = 1.0F / f;
         int i = pStack.getCount();
         int j = 0;

         for(int k = 0; k < i; ++k) {
            if (randomsource.nextFloat() <= f1) {
               ++j;
            }
         }

         pStack.setCount(j);
      }

      return pStack;
   }

   public static LootItemConditionalFunction.Builder<?> explosionDecay() {
      return simpleBuilder(ApplyExplosionDecay::new);
   }
}