package net.minecraft.world.level.storage.loot.functions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Set;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

/**
 * A LootItemFunction that limits the stack's count to fall within a given {@link IntRange}.
 */
public class LimitCount extends LootItemConditionalFunction {
   public static final Codec<LimitCount> CODEC = RecordCodecBuilder.create((p_297107_) -> {
      return commonFields(p_297107_).and(IntRange.CODEC.fieldOf("limit").forGetter((p_297106_) -> {
         return p_297106_.limiter;
      })).apply(p_297107_, LimitCount::new);
   });
   private final IntRange limiter;

   private LimitCount(List<LootItemCondition> p_298546_, IntRange p_165214_) {
      super(p_298546_);
      this.limiter = p_165214_;
   }

   public LootItemFunctionType getType() {
      return LootItemFunctions.LIMIT_COUNT;
   }

   /**
    * Get the parameters used by this object.
    */
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return this.limiter.getReferencedContextParams();
   }

   /**
    * Called to perform the actual action of this function, after conditions have been checked.
    */
   public ItemStack run(ItemStack pStack, LootContext pContext) {
      int i = this.limiter.clamp(pContext, pStack.getCount());
      pStack.setCount(i);
      return pStack;
   }

   public static LootItemConditionalFunction.Builder<?> limitCount(IntRange pCountLimit) {
      return simpleBuilder((p_297105_) -> {
         return new LimitCount(p_297105_, pCountLimit);
      });
   }
}