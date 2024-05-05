package net.minecraft.world.level.storage.loot.providers.number;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Set;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.providers.score.ContextScoreboardNameProvider;
import net.minecraft.world.level.storage.loot.providers.score.ScoreboardNameProvider;
import net.minecraft.world.level.storage.loot.providers.score.ScoreboardNameProviders;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;

public record ScoreboardValue(ScoreboardNameProvider target, String score, float scale) implements NumberProvider {
   public static final Codec<ScoreboardValue> CODEC = RecordCodecBuilder.create((p_297867_) -> {
      return p_297867_.group(ScoreboardNameProviders.CODEC.fieldOf("target").forGetter(ScoreboardValue::target), Codec.STRING.fieldOf("score").forGetter(ScoreboardValue::score), Codec.FLOAT.fieldOf("scale").orElse(1.0F).forGetter(ScoreboardValue::scale)).apply(p_297867_, ScoreboardValue::new);
   });

   public LootNumberProviderType getType() {
      return NumberProviders.SCORE;
   }

   /**
    * Get the parameters used by this object.
    */
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return this.target.getReferencedContextParams();
   }

   public static ScoreboardValue fromScoreboard(LootContext.EntityTarget pEntityTarget, String pScore) {
      return fromScoreboard(pEntityTarget, pScore, 1.0F);
   }

   public static ScoreboardValue fromScoreboard(LootContext.EntityTarget pEntityTarget, String pScore, float pScale) {
      return new ScoreboardValue(ContextScoreboardNameProvider.forTarget(pEntityTarget), pScore, pScale);
   }

   public float getFloat(LootContext pLootContext) {
      String s = this.target.getScoreboardName(pLootContext);
      if (s == null) {
         return 0.0F;
      } else {
         Scoreboard scoreboard = pLootContext.getLevel().getScoreboard();
         Objective objective = scoreboard.getObjective(this.score);
         if (objective == null) {
            return 0.0F;
         } else {
            return !scoreboard.hasPlayerScore(s, objective) ? 0.0F : (float)scoreboard.getOrCreatePlayerScore(s, objective).getScore() * this.scale;
         }
      }
   }
}