package net.minecraft.data.recipes.packs;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class UpdateOneTwentyOneRecipeProvider extends RecipeProvider {
   public UpdateOneTwentyOneRecipeProvider(PackOutput p_312536_) {
      super(p_312536_);
   }

   protected void buildRecipes(RecipeOutput p_310714_) {
      generateForEnabledBlockFamilies(p_310714_, FeatureFlagSet.of(FeatureFlags.UPDATE_1_21));
      ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Blocks.CRAFTER).define('#', Items.IRON_INGOT).define('C', Items.CRAFTING_TABLE).define('R', Items.REDSTONE).define('D', Items.DROPPER).pattern("###").pattern("#C#").pattern("RDR").unlockedBy("has_dropper", has(Items.DROPPER)).save(p_310714_);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.TUFF_SLAB, Blocks.TUFF, 2);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.TUFF_STAIRS, Blocks.TUFF);
      stonecutterResultFromBase(p_310714_, RecipeCategory.DECORATIONS, Blocks.TUFF_WALL, Blocks.TUFF);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.CHISELED_TUFF, Blocks.TUFF);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.POLISHED_TUFF, Blocks.TUFF);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.POLISHED_TUFF_SLAB, Blocks.TUFF, 2);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.POLISHED_TUFF_STAIRS, Blocks.TUFF);
      stonecutterResultFromBase(p_310714_, RecipeCategory.DECORATIONS, Blocks.POLISHED_TUFF_WALL, Blocks.TUFF);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.TUFF_BRICKS, Blocks.TUFF);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.TUFF_BRICK_SLAB, Blocks.TUFF, 2);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.TUFF_BRICK_STAIRS, Blocks.TUFF);
      stonecutterResultFromBase(p_310714_, RecipeCategory.DECORATIONS, Blocks.TUFF_BRICK_WALL, Blocks.TUFF);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.CHISELED_TUFF_BRICKS, Blocks.TUFF);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.POLISHED_TUFF_SLAB, Blocks.POLISHED_TUFF, 2);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.POLISHED_TUFF_STAIRS, Blocks.POLISHED_TUFF);
      stonecutterResultFromBase(p_310714_, RecipeCategory.DECORATIONS, Blocks.POLISHED_TUFF_WALL, Blocks.POLISHED_TUFF);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.TUFF_BRICKS, Blocks.POLISHED_TUFF);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.TUFF_BRICK_SLAB, Blocks.POLISHED_TUFF, 2);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.TUFF_BRICK_STAIRS, Blocks.POLISHED_TUFF);
      stonecutterResultFromBase(p_310714_, RecipeCategory.DECORATIONS, Blocks.TUFF_BRICK_WALL, Blocks.POLISHED_TUFF);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.CHISELED_TUFF_BRICKS, Blocks.POLISHED_TUFF);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.TUFF_BRICK_SLAB, Blocks.TUFF_BRICKS, 2);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.TUFF_BRICK_STAIRS, Blocks.TUFF_BRICKS);
      stonecutterResultFromBase(p_310714_, RecipeCategory.DECORATIONS, Blocks.TUFF_BRICK_WALL, Blocks.TUFF_BRICKS);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.CHISELED_TUFF_BRICKS, Blocks.TUFF_BRICKS);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.CHISELED_COPPER, Blocks.COPPER_BLOCK, 4);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.EXPOSED_CHISELED_COPPER, Blocks.EXPOSED_COPPER, 4);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.WEATHERED_CHISELED_COPPER, Blocks.WEATHERED_COPPER, 4);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.OXIDIZED_CHISELED_COPPER, Blocks.OXIDIZED_COPPER, 4);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.WAXED_CHISELED_COPPER, Blocks.WAXED_COPPER_BLOCK, 4);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.WAXED_EXPOSED_CHISELED_COPPER, Blocks.WAXED_EXPOSED_COPPER, 4);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.WAXED_WEATHERED_CHISELED_COPPER, Blocks.WAXED_WEATHERED_COPPER, 4);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.WAXED_OXIDIZED_CHISELED_COPPER, Blocks.WAXED_OXIDIZED_COPPER, 4);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.CHISELED_COPPER, Blocks.CUT_COPPER, 1);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.EXPOSED_CHISELED_COPPER, Blocks.EXPOSED_CUT_COPPER, 1);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.WEATHERED_CHISELED_COPPER, Blocks.WEATHERED_CUT_COPPER, 1);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.OXIDIZED_CHISELED_COPPER, Blocks.OXIDIZED_CUT_COPPER, 1);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.WAXED_CHISELED_COPPER, Blocks.WAXED_CUT_COPPER, 1);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.WAXED_EXPOSED_CHISELED_COPPER, Blocks.WAXED_EXPOSED_CUT_COPPER, 1);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.WAXED_WEATHERED_CHISELED_COPPER, Blocks.WAXED_WEATHERED_CUT_COPPER, 1);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.WAXED_OXIDIZED_CHISELED_COPPER, Blocks.WAXED_OXIDIZED_CUT_COPPER, 1);
      grate(p_310714_, Blocks.COPPER_GRATE, Blocks.COPPER_BLOCK);
      grate(p_310714_, Blocks.EXPOSED_COPPER_GRATE, Blocks.EXPOSED_COPPER);
      grate(p_310714_, Blocks.WEATHERED_COPPER_GRATE, Blocks.WEATHERED_COPPER);
      grate(p_310714_, Blocks.OXIDIZED_COPPER_GRATE, Blocks.OXIDIZED_COPPER);
      grate(p_310714_, Blocks.WAXED_COPPER_GRATE, Blocks.WAXED_COPPER_BLOCK);
      grate(p_310714_, Blocks.WAXED_EXPOSED_COPPER_GRATE, Blocks.WAXED_EXPOSED_COPPER);
      grate(p_310714_, Blocks.WAXED_WEATHERED_COPPER_GRATE, Blocks.WAXED_WEATHERED_COPPER);
      grate(p_310714_, Blocks.WAXED_OXIDIZED_COPPER_GRATE, Blocks.WAXED_OXIDIZED_COPPER);
      copperBulb(p_310714_, Blocks.COPPER_BULB, Blocks.COPPER_BLOCK);
      copperBulb(p_310714_, Blocks.EXPOSED_COPPER_BULB, Blocks.EXPOSED_COPPER);
      copperBulb(p_310714_, Blocks.WEATHERED_COPPER_BULB, Blocks.WEATHERED_COPPER);
      copperBulb(p_310714_, Blocks.OXIDIZED_COPPER_BULB, Blocks.OXIDIZED_COPPER);
      copperBulb(p_310714_, Blocks.WAXED_COPPER_BULB, Blocks.WAXED_COPPER_BLOCK);
      copperBulb(p_310714_, Blocks.WAXED_EXPOSED_COPPER_BULB, Blocks.WAXED_EXPOSED_COPPER);
      copperBulb(p_310714_, Blocks.WAXED_WEATHERED_COPPER_BULB, Blocks.WAXED_WEATHERED_COPPER);
      copperBulb(p_310714_, Blocks.WAXED_OXIDIZED_COPPER_BULB, Blocks.WAXED_OXIDIZED_COPPER);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.COPPER_GRATE, Blocks.COPPER_BLOCK, 4);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.EXPOSED_COPPER_GRATE, Blocks.EXPOSED_COPPER, 4);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.WEATHERED_COPPER_GRATE, Blocks.WEATHERED_COPPER, 4);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.OXIDIZED_COPPER_GRATE, Blocks.OXIDIZED_COPPER, 4);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.WAXED_COPPER_GRATE, Blocks.WAXED_COPPER_BLOCK, 4);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.WAXED_EXPOSED_COPPER_GRATE, Blocks.WAXED_EXPOSED_COPPER, 4);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.WAXED_WEATHERED_COPPER_GRATE, Blocks.WAXED_WEATHERED_COPPER, 4);
      stonecutterResultFromBase(p_310714_, RecipeCategory.BUILDING_BLOCKS, Blocks.WAXED_OXIDIZED_COPPER_GRATE, Blocks.WAXED_OXIDIZED_COPPER, 4);
      waxRecipes(p_310714_, FeatureFlagSet.of(FeatureFlags.UPDATE_1_21));
   }
}