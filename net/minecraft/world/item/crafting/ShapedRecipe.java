package net.minecraft.world.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ShapedRecipe implements CraftingRecipe, net.minecraftforge.common.crafting.IShapedRecipe<CraftingContainer> {
   static int MAX_WIDTH = 3;
   static int MAX_HEIGHT = 3;
   /**
    * Expand the max width and height allowed in the deserializer.
    * This should be called by modders who add custom crafting tables that are larger than the vanilla 3x3.
    * @param width your max recipe width
    * @param height your max recipe height
    */
   public static void setCraftingSize(int width, int height) {
      if (MAX_WIDTH < width) MAX_WIDTH = width;
      if (MAX_HEIGHT < height) MAX_HEIGHT = height;
   }

   final ShapedRecipePattern pattern;
   final ItemStack result;
   final String group;
   final CraftingBookCategory category;
   final boolean showNotification;

   public ShapedRecipe(String pGroup, CraftingBookCategory pCategory, ShapedRecipePattern pPattern, ItemStack pResult, boolean pShowNotification) {
      this.group = pGroup;
      this.category = pCategory;
      this.pattern = pPattern;
      this.result = pResult;
      this.showNotification = pShowNotification;
   }

   public ShapedRecipe(String pGroup, CraftingBookCategory pCategory, ShapedRecipePattern pPattern, ItemStack pResult) {
      this(pGroup, pCategory, pPattern, pResult, true);
   }

   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.SHAPED_RECIPE;
   }

   /**
    * Recipes with equal group are combined into one button in the recipe book
    */
   public String getGroup() {
      return this.group;
   }

   @Override
   public int getRecipeWidth() {
      return getWidth();
   }

   public CraftingBookCategory category() {
      return this.category;
   }

   @Override
   public int getRecipeHeight() {
      return getHeight();
   }

   public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
      return this.result;
   }

   public NonNullList<Ingredient> getIngredients() {
      return this.pattern.ingredients();
   }

   public boolean showNotification() {
      return this.showNotification;
   }

   /**
    * Used to determine if this recipe can fit in a grid of the given width/height
    */
   public boolean canCraftInDimensions(int pWidth, int pHeight) {
      return pWidth >= this.pattern.width() && pHeight >= this.pattern.height();
   }

   /**
    * Used to check if a recipe matches current crafting inventory
    */
   public boolean matches(CraftingContainer pInv, Level pLevel) {
      return this.pattern.matches(pInv);
   }

   public ItemStack assemble(CraftingContainer pContainer, RegistryAccess pRegistryAccess) {
      return this.getResultItem(pRegistryAccess).copy();
   }

   public int getWidth() {
      return this.pattern.width();
   }

   public int getHeight() {
      return this.pattern.height();
   }

   public boolean isIncomplete() {
      NonNullList<Ingredient> nonnulllist = this.getIngredients();
      return nonnulllist.isEmpty() || nonnulllist.stream().filter((p_151277_) -> {
         return !p_151277_.isEmpty();
      }).anyMatch((p_151273_) -> {
         return net.minecraftforge.common.ForgeHooks.hasNoElements(p_151273_);
      });
   }

   public static class Serializer implements RecipeSerializer<ShapedRecipe> {
      public static final Codec<ShapedRecipe> CODEC = RecordCodecBuilder.create((p_309256_) -> {
         return p_309256_.group(ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter((p_309251_) -> {
            return p_309251_.group;
         }), CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter((p_309253_) -> {
            return p_309253_.category;
         }), ShapedRecipePattern.MAP_CODEC.forGetter((p_309254_) -> {
            return p_309254_.pattern;
         }), ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("result").forGetter((p_309252_) -> {
            return p_309252_.result;
         }), ExtraCodecs.strictOptionalField(Codec.BOOL, "show_notification", true).forGetter((p_309255_) -> {
            return p_309255_.showNotification;
         })).apply(p_309256_, ShapedRecipe::new);
      });

      public Codec<ShapedRecipe> codec() {
         return CODEC;
      }

      public ShapedRecipe fromNetwork(FriendlyByteBuf pBuffer) {
         String s = pBuffer.readUtf();
         CraftingBookCategory craftingbookcategory = pBuffer.readEnum(CraftingBookCategory.class);
         ShapedRecipePattern shapedrecipepattern = ShapedRecipePattern.fromNetwork(pBuffer);
         ItemStack itemstack = pBuffer.readItem();
         boolean flag = pBuffer.readBoolean();
         return new ShapedRecipe(s, craftingbookcategory, shapedrecipepattern, itemstack, flag);
      }

      public void toNetwork(FriendlyByteBuf pBuffer, ShapedRecipe pRecipe) {
         pBuffer.writeUtf(pRecipe.group);
         pBuffer.writeEnum(pRecipe.category);
         pRecipe.pattern.toNetwork(pBuffer);
         pBuffer.writeItem(pRecipe.result);
         pBuffer.writeBoolean(pRecipe.showNotification);
      }
   }
}
