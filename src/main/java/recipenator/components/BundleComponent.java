package recipenator.components;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import recipenator.api.component.IRecipeComponent;
import recipenator.api.component.RecipeComponentBase;

import java.util.List;

public class BundleComponent extends RecipeComponentBase<List<ItemStack>> {
    public BundleComponent(IRecipeComponent left, IRecipeComponent right) {

    }

    @Override
    public List<ItemStack> getRecipeItem() {
        return null;
    }

    @Override
    public List<ItemStack> getAllItems() {
        return null;
    }

    @Override
    public boolean equals(ItemStack component) {
        return false;
    }

    @Override
    protected IRecipeComponent<List<ItemStack>> newInstance(int count, int meta, NBTTagCompound tag) {
        return null;
    }
}
