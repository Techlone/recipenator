package recipenator.lualib.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import recipenator.api.IRecipeComponent;
import recipenator.api.annotation.Metamethod;
import recipenator.lualib.item.NullComponent;
import recipenator.utils.RecipeHelper;
import recipenator.utils.lua.MetamethodType;

import java.util.ArrayList;
import java.util.List;

public class RecipeManager {
    public final static List<IRecipe> AddedRecipes = new ArrayList<>();

    public static void removeAll() {
        List recipeList = CraftingManager.getInstance().getRecipeList();
        for (IRecipe recipe : AddedRecipes)
            recipeList.remove(recipe);
        AddedRecipes.clear();
    }

    public void addRecipe(IRecipe recipe) {
        GameRegistry.addRecipe(recipe);
        AddedRecipes.add(recipe);
    }

    public void addShaped(IRecipeComponent<ItemStack> result, IRecipeComponent[][] input) {
        RecipeHelper.Size size = RecipeHelper.rectangulate(input);
        IRecipeComponent[] items = new IRecipeComponent[size.width * size.height];
        int i = 0;
        for (IRecipeComponent[] line : input) {
            for (IRecipeComponent component : line) {
                items[i++] = component == null ? NullComponent.NULL : component;
            }
        }
        addRecipe(new RecipeShaped(size, items, result));
    }

    public void addShapeless(IRecipeComponent<ItemStack> result, IRecipeComponent[] input) {
        boolean hasNull = false;
        int count = 0;
        for (IRecipeComponent component : input) {
            if (NullComponent.isNull(component)) hasNull = true;
            else count++;
        }
        if (hasNull) {
            IRecipeComponent[] data = new IRecipeComponent[count];
            for (IRecipeComponent component : input) {
                if (NullComponent.isNull(component)) continue;
                data[--count] = component;
            }
            input = data;
        }
        addRecipe(new RecipeShapeless(input, result));
    }

    @Metamethod(MetamethodType.INDEX)
    public Object index(String id) {
        return null; //TODO mod integration
    }
}
