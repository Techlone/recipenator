package recipenator.api.component;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import recipenator.api.extention.LuaName;

public abstract class RecipeComponentBase<T> implements IRecipeComponent<T> {
    public static int anyMeta = OreDictionary.WILDCARD_VALUE;

    protected final int count;
    protected final int meta;
    protected final NBTTagCompound tag;

    protected RecipeComponentBase() {
        this(1, 0, null);
    }

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

    protected Object index(Object id) {
        return null;
    }

    protected Object index(String id) {
        return id.equals("any") ? setMeta(anyMeta) : null;
    }

    protected IRecipeComponent<T> setMeta(int meta) {
        return this.meta == meta ? this : newInstance(count, meta < anyMeta ? meta : anyMeta, tag);
    }

    public final Object __mul(Object operand) {
        return operand instanceof Integer ? increaseCount((int) operand) : mul(operand);
    }

    protected Object mul(Object operand) {
        return null;
    }

    protected IRecipeComponent<T> increaseCount(int multiplier) {
        return multiplier == 1 ? this : newInstance(count * multiplier, meta, tag);
    }

    @LuaName("tag")
    public IRecipeComponent<T> setTag(NBTTagCompound tag) {
        return !tag.equals(this.tag) ? newInstance(count, meta, tag) : this;
    }

    public final Object __concat(Object component) {
        return null;
    }

    protected abstract IRecipeComponent<T> newInstance(int count, int meta, NBTTagCompound tag);
}
