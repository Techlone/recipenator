package recipenator.api.component;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import recipenator.api.metamethod.Metamethod;
import recipenator.api.metamethod.MetamethodType;

public abstract class RecipeComponentBase<T> implements IRecipeComponent<T> {
    public static int anyMeta = OreDictionary.WILDCARD_VALUE;

    protected final int count;
    protected final int meta;
    protected final NBTTagCompound tag;

    protected RecipeComponentBase(int count, int meta, NBTTagCompound tag) {
        this.count = count;
        this.meta = meta;
        this.tag = tag == null ? null : (NBTTagCompound) tag.copy();
    }

    @Metamethod(MetamethodType.INDEX)
    public final Object index(Object id) {
        return id instanceof Integer ? setMeta((int) id) : index(String.valueOf(id));
    }

    public Object index(String id) {
        return id.equals("tag") ? tag : id.equals("any") ? setMeta(anyMeta) : null;
    }

    @Override
    @Metamethod(MetamethodType.MUL)
    public IRecipeComponent<T> multiply(int multiplier) {
        return multiplier == 1 ? this : newInstance(count * multiplier, meta, tag);
    }

    @Override
    public IRecipeComponent<T> setMeta(int meta) {
        return this.meta == meta ? this : newInstance(count, meta < anyMeta ? meta : anyMeta, tag);
    }

    @Override
    public IRecipeComponent<T> setTag(NBTTagCompound tag) {
        return this.tag.equals(tag) ? this : newInstance(count, meta, tag);
    }

    protected abstract IRecipeComponent<T> newInstance(int count, int meta, NBTTagCompound tag);
}
