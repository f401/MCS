package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

/**
 * LootItemFunction that applies the {@code "SkullOwner"} NBT tag to any player heads based on the given {@link
 * LootContext.EntityTarget}.
 * If the given target does not resolve to a player, nothing happens.
 */
public class FillPlayerHead extends LootItemConditionalFunction {
   public static final Codec<FillPlayerHead> CODEC = RecordCodecBuilder.create((p_297099_) -> {
      return commonFields(p_297099_).and(LootContext.EntityTarget.CODEC.fieldOf("entity").forGetter((p_297096_) -> {
         return p_297096_.entityTarget;
      })).apply(p_297099_, FillPlayerHead::new);
   });
   private final LootContext.EntityTarget entityTarget;

   public FillPlayerHead(List<LootItemCondition> p_301112_, LootContext.EntityTarget p_80605_) {
      super(p_301112_);
      this.entityTarget = p_80605_;
   }

   public LootItemFunctionType getType() {
      return LootItemFunctions.FILL_PLAYER_HEAD;
   }

   /**
    * Get the parameters used by this object.
    */
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return ImmutableSet.of(this.entityTarget.getParam());
   }

   /**
    * Called to perform the actual action of this function, after conditions have been checked.
    */
   public ItemStack run(ItemStack pStack, LootContext pContext) {
      if (pStack.is(Items.PLAYER_HEAD)) {
         Object $$3 = pContext.getParamOrNull(this.entityTarget.getParam());
         if ($$3 instanceof Player) {
            Player player = (Player)$$3;
            GameProfile gameprofile = player.getGameProfile();
            pStack.getOrCreateTag().put("SkullOwner", NbtUtils.writeGameProfile(new CompoundTag(), gameprofile));
         }
      }

      return pStack;
   }

   public static LootItemConditionalFunction.Builder<?> fillPlayerHead(LootContext.EntityTarget pEntityTarget) {
      return simpleBuilder((p_297098_) -> {
         return new FillPlayerHead(p_297098_, pEntityTarget);
      });
   }
}