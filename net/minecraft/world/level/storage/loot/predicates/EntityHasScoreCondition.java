package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;

public record EntityHasScoreCondition(Map<String, IntRange> scores, LootContext.EntityTarget entityTarget) implements LootItemCondition {
   public static final Codec<EntityHasScoreCondition> CODEC = RecordCodecBuilder.create((p_297188_) -> {
      return p_297188_.group(Codec.unboundedMap(Codec.STRING, IntRange.CODEC).fieldOf("scores").forGetter(EntityHasScoreCondition::scores), LootContext.EntityTarget.CODEC.fieldOf("entity").forGetter(EntityHasScoreCondition::entityTarget)).apply(p_297188_, EntityHasScoreCondition::new);
   });

   public LootItemConditionType getType() {
      return LootItemConditions.ENTITY_SCORES;
   }

   /**
    * Get the parameters used by this object.
    */
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return Stream.concat(Stream.of(this.entityTarget.getParam()), this.scores.values().stream().flatMap((p_165487_) -> {
         return p_165487_.getReferencedContextParams().stream();
      })).collect(ImmutableSet.toImmutableSet());
   }

   public boolean test(LootContext pContext) {
      Entity entity = pContext.getParamOrNull(this.entityTarget.getParam());
      if (entity == null) {
         return false;
      } else {
         Scoreboard scoreboard = entity.level().getScoreboard();

         for(Map.Entry<String, IntRange> entry : this.scores.entrySet()) {
            if (!this.hasScore(pContext, entity, scoreboard, entry.getKey(), entry.getValue())) {
               return false;
            }
         }

         return true;
      }
   }

   protected boolean hasScore(LootContext pLootContext, Entity pTargetEntity, Scoreboard pScoreboard, String pObjectiveName, IntRange pScoreRange) {
      Objective objective = pScoreboard.getObjective(pObjectiveName);
      if (objective == null) {
         return false;
      } else {
         String s = pTargetEntity.getScoreboardName();
         return !pScoreboard.hasPlayerScore(s, objective) ? false : pScoreRange.test(pLootContext, pScoreboard.getOrCreatePlayerScore(s, objective).getScore());
      }
   }

   public static EntityHasScoreCondition.Builder hasScores(LootContext.EntityTarget pEntityTarget) {
      return new EntityHasScoreCondition.Builder(pEntityTarget);
   }

   public static class Builder implements LootItemCondition.Builder {
      private final ImmutableMap.Builder<String, IntRange> scores = ImmutableMap.builder();
      private final LootContext.EntityTarget entityTarget;

      public Builder(LootContext.EntityTarget pEntityTarget) {
         this.entityTarget = pEntityTarget;
      }

      /**
       * Add a check that the score for the given {@code objectiveName} is within {@code scoreRange}.
       */
      public EntityHasScoreCondition.Builder withScore(String pObjectiveName, IntRange pScoreRange) {
         this.scores.put(pObjectiveName, pScoreRange);
         return this;
      }

      public LootItemCondition build() {
         return new EntityHasScoreCondition(this.scores.build(), this.entityTarget);
      }
   }
}