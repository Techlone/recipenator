package recipenator.lualib.oredict;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import org.luaj.vm2.ast.Str;
import recipenator.api.component.BaseRecipeComponent;
import recipenator.api.component.IRecipeComponent;
import recipenator.lualib.item.ItemComponent;
import recipenator.utils.NamesTree;
import recipenator.utils.RecipeHelper;

import java.util.List;

public class OreComponent extends BaseRecipeComponent<List<ItemStack>> {
    private final String id;

    public OreComponent(String id) {
        this(id, 1, 0, null);
    }

    public OreComponent(String id, int count, int meta, NBTTagCompound tag) {
        super(count, meta, tag);
        this.id = id;
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
}
