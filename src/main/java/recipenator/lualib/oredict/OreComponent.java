package recipenator.lualib.oredict;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import recipenator.api.IRecipeComponent;
import recipenator.api.annotation.Metamethod;
import recipenator.utils.RecipeHelper;
import recipenator.utils.lua.MetamethodType;

import java.util.List;

public class OreComponent implements IRecipeComponent<List<ItemStack>> {
    static int anyMeta = OreDictionary.WILDCARD_VALUE;

    private final String id;
    private final int count;
    private final int meta;
    private final NBTTagCompound tag;

    public OreComponent(String id) {
        this(id, 1, 0, null);
    }

    public OreComponent(String id, int count, int meta, NBTTagCompound tag) {
        this.id = id;
        this.count = count;
        this.meta = meta;
        this.tag = tag == null ? null : (NBTTagCompound) tag.copy();
    }

    @Override
    public List<ItemStack> getRecipeItem() {
        return OreDictionary.getOres(id);
    }

    @Override
    @Metamethod(MetamethodType.MUL)
    public Object mulCount(int multiplier) {
        return changeCount(this.count * multiplier);
    }

    @Metamethod(MetamethodType.INDEX)
    public Object index(Object id) {
        if (id instanceof Integer)
            return withMeta((int) id);
        return index(String.valueOf(id));
    }

    public Object index(String id) {
        if (id.equals("tag"))
            return tag;
        if (id.equals("any"))
            return changeMeta(anyMeta);
        return null;
    }

    @Override
    public Object withMeta(int meta) {
        return changeMeta(meta);
    }

    @Override
    public boolean equals(ItemStack inputItem) {
        for (ItemStack recipeItem : this.getRecipeItem())
            if (RecipeHelper.isEquals(recipeItem, inputItem))
                return true;
        return false;
    }

    private OreComponent changeCount(int count) {
        if (this.count == count) return this;
        return new OreComponent(id, count, meta, tag);
    }

    private OreComponent changeMeta(int meta) {
        if (this.meta == meta) return this;
        return new OreComponent(id, count, meta, tag);
    }
}
