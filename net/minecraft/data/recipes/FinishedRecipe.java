package net.minecraft.data.recipes;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

public interface FinishedRecipe {
   void serializeRecipeData(JsonObject pJson);

   /**
    * Gets the JSON for the recipe.
    */
   default JsonObject serializeRecipe() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("type", BuiltInRegistries.RECIPE_SERIALIZER.getKey(this.type()).toString());
      this.serializeRecipeData(jsonobject);
      return jsonobject;
   }

   ResourceLocation id();

   RecipeSerializer<?> type();

   @Nullable
   AdvancementHolder advancement();

   @Nullable
   default AdvancementData advancementData() {
       var adv = this.advancement();
       return adv == null ? null : new AdvancementData(adv.id(), adv.value().serializeToJson());
   }

   record AdvancementData(ResourceLocation id, JsonObject data) {}
}
