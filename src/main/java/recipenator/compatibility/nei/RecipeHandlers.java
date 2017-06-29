package recipenator.compatibility.nei;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.api.API;
import codechicken.nei.recipe.ShapedRecipeHandler;
import codechicken.nei.recipe.ShapelessRecipeHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import recipenator.recipes.RecipeShaped;
import recipenator.recipes.RecipeShapeless;
import recipenator.utils.RecipeHelper;

import java.util.List;

public class RecipeHandlers {
    public static void registerRecipe() {
        API.registerRecipeHandler(new ShapedHandler());
        API.registerUsageHandler(new ShapedHandler());
        API.registerRecipeHandler(new ShapelessHandler());
        API.registerUsageHandler(new ShapelessHandler());
    }

    public static class ShapelessHandler extends ShapelessRecipeHandler {
        @Override
        public void loadCraftingRecipes(String outputId, Object... results) {
            if (outputId.equals("crafting") && getClass() == ShapelessHandler.class) {
                List recipeList = CraftingManager.getInstance().getRecipeList();
                for (Object recipe : recipeList) {
                    if (!(recipe instanceof RecipeShapeless)) continue;
                    CachedShapelessRecipe cache = toCachedRecipe((RecipeShapeless) recipe);
                    arecipes.add(cache);
                }
            } else {
                super.loadCraftingRecipes(outputId, results);
            }
        }

        @Override
        public void loadCraftingRecipes(ItemStack result) {
            List recipeList = CraftingManager.getInstance().getRecipeList();
            for (Object recipe : recipeList) {
                if (!(recipe instanceof RecipeShapeless)) continue;

                RecipeShapeless recipeShapeless = (RecipeShapeless) recipe;
                if (!NEIServerUtils.areStacksSameTypeCrafting(recipeShapeless.getRecipeOutput(), result)) continue;

                CachedShapelessRecipe cache = toCachedRecipe(recipeShapeless);
                arecipes.add(cache);
            }
        }

        @Override
        public void loadUsageRecipes(ItemStack ingredient) {
            List recipeList = CraftingManager.getInstance().getRecipeList();
            for (Object recipe : recipeList) {
                if (!(recipe instanceof RecipeShapeless)) continue;

                CachedShapelessRecipe cache = toCachedRecipe((RecipeShapeless) recipe);
                if (cache.contains(cache.ingredients, ingredient)) {
                    cache.setIngredientPermutation(cache.ingredients, ingredient);
                    this.arecipes.add(cache);
                }
            }
        }

        private CachedShapelessRecipe toCachedRecipe(RecipeShapeless recipe) {
            return new CachedShapelessRecipe(recipe.getRecipeInputs(), recipe.getRecipeOutput());
        }
    }

    public static class ShapedHandler extends ShapedRecipeHandler {
        @Override
        public void loadCraftingRecipes(String outputId, Object... results) {
            if (outputId.equals("crafting") && getClass() == ShapedHandler.class) {
                List recipeList = CraftingManager.getInstance().getRecipeList();
                for (Object irecipe : recipeList) {
                    if (!(irecipe instanceof RecipeShaped)) continue;
                    CachedShapedRecipe recipe = toCachedRecipe((RecipeShaped) irecipe);
                    recipe.computeVisuals();
                    arecipes.add(recipe);
                }
            } else {
                super.loadCraftingRecipes(outputId, results);
            }
        }

        @Override
        public void loadCraftingRecipes(ItemStack result) {
            List recipeList = CraftingManager.getInstance().getRecipeList();
            for (Object recipe : recipeList) {
                if (!(recipe instanceof RecipeShaped)) continue;

                RecipeShaped recipeShaped = (RecipeShaped) recipe;
                if (!NEIServerUtils.areStacksSameTypeCrafting(recipeShaped.getRecipeOutput(), result)) continue;

                CachedShapedRecipe cache = toCachedRecipe(recipeShaped);
                cache.computeVisuals();
                arecipes.add(cache);
            }
        }

        @Override
        public void loadUsageRecipes(ItemStack ingredient) {
            List recipeList = CraftingManager.getInstance().getRecipeList();
            for (Object recipe : recipeList) {
                if (!(recipe instanceof RecipeShaped)) continue;

                CachedShapedRecipe cache = toCachedRecipe((RecipeShaped) recipe);
                if (!cache.contains(cache.ingredients, ingredient.getItem())) continue;

                cache.computeVisuals();
                if (cache.contains(cache.ingredients, ingredient)) {
                    cache.setIngredientPermutation(cache.ingredients, ingredient);
                    this.arecipes.add(cache);
                }
            }
        }

        private CachedShapedRecipe toCachedRecipe(RecipeShaped recipe) {
            RecipeHelper.Size size = recipe.getSize();
            return new CachedShapedRecipe(size.width, size.height, recipe.getRecipeInputs(), recipe.getRecipeOutput());
        }
    }
}
