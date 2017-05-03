package recipenator.lualib.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import recipenator.api.component.IRecipeComponent;
import recipenator.api.metamethod.Metamethod;
import recipenator.lualib.item.ItemComponent;
import recipenator.lualib.item.NullComponent;
import recipenator.utils.NamesTree;
import recipenator.utils.RecipeHelper;
import recipenator.api.metamethod.MetamethodType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecipeManager {
    public final static RecipeManager instance = new RecipeManager();

    public final ItemComponent item = new ItemComponent(new NamesTree.NameNode(), 1, 0, null);

    protected final List<IRecipe> addedRecipes = Collections.synchronizedList(new ArrayList<>());

    @SuppressWarnings("unchecked")
    public void removeAll() {
        List<IRecipe> recipeList = CraftingManager.getInstance().getRecipeList();
        recipeList.removeAll(addedRecipes);
        addedRecipes.clear();
    }

    public void addRecipe(IRecipe recipe) {
        GameRegistry.addRecipe(recipe);
        addedRecipes.add(recipe);
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
