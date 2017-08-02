package recipenator.components;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import recipenator.api.component.IRecipeComponent;
import recipenator.api.component.RecipeComponentBase;
import recipenator.utils.RecipeHelper;

import java.util.List;
import java.util.stream.Collectors;

public class OreComponent extends RecipeComponentBase<List<ItemStack>> {
    protected final String id;

    public OreComponent(String id) {
        this(id, 1, 0, null);
    }

    public OreComponent(String id, int count, int meta, NBTTagCompound tag) {
        super(count, meta, tag);
        this.id = id;
    }

    public void add(ItemComponent... items) {
        for (ItemComponent item : items) {
            OreDictionary.registerOre(id, item.getRecipeItem());
        }
    }

    public List<ItemComponent> getComponents() {
        return getRecipeItem().stream().map(ItemComponent::fromItemStack).collect(Collectors.toList());
    }

    @Override
    public List<ItemStack> getRecipeItem() {
        return OreDictionary.getOres(id);
    }

    @Override
    public boolean equals(ItemStack inputItem) {
        for (ItemStack recipeItem : this.getRecipeItem())
            if (RecipeHelper.isEquals(recipeItem, inputItem))
                return true;
        return false;
    }

    @Override
    protected IRecipeComponent<List<ItemStack>> newInstance(int count, int meta, NBTTagCompound tag) {
        return new OreComponent(id, count, meta, tag);
    }

    @Override
    public List<ItemStack> getAllItems() {
        return getRecipeItem();
    }
}
