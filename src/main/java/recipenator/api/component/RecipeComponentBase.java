package recipenator.api.component;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

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

    public final Object __index(Object id) {
        if (id instanceof Integer) return setMeta((int) id);
        if (id instanceof String) return index((String) id);
        return index(id);
    }

    public Object index(Object id) {
        return null;
    }

    public Object index(String id) {
        return id.equals("tag") ? tag : id.equals("any") ? setMeta(anyMeta) : null;
    }

    public final Object __mul(Object operand) {
        return operand instanceof Integer ? increaseCount((int) operand) : mul(operand);
    }

    private Object mul(Object operand) {
        return null;
    }

    public IRecipeComponent<T> increaseCount(int multiplier) {
        return multiplier == 1 ? this : newInstance(count * multiplier, meta, tag);
    }

    public IRecipeComponent<T> setMeta(int meta) {
        return this.meta == meta ? this : newInstance(count, meta < anyMeta ? meta : anyMeta, tag);
    }

    public IRecipeComponent<T> setTag(NBTTagCompound tag) {
        return this.tag.equals(tag) ? this : newInstance(count, meta, tag);
    }

    protected abstract IRecipeComponent<T> newInstance(int count, int meta, NBTTagCompound tag);
}
