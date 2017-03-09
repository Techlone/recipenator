package recipenator.lualib.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import recipenator.api.IRecipeComponent;
import recipenator.api.annotation.Metamethod;
import recipenator.utils.NamesTree;
import recipenator.utils.RecipeHelper;
import recipenator.utils.lua.MetamethodType;

public class ItemComponent implements IRecipeComponent<ItemStack> {
    static int anyMeta = OreDictionary.WILDCARD_VALUE;

    private final NamesTree.NameNode node;
    private final int count;
    private final int meta;
    private final NBTTagCompound tag;

    public ItemComponent(NamesTree.NameNode node) {
        this(node, 1, 0, null);
    }

    public ItemComponent(NamesTree.NameNode node, int count, int meta, NBTTagCompound tag) {
        this.node = node;
        this.count = count;
        this.meta = meta;
        this.tag = tag == null ? null : (NBTTagCompound) tag.copy();
    }

    @Override
    public ItemStack getRecipeItem() {
        String name = node.getName();
        if (name == null) return null;

        Item item = (Item) Item.itemRegistry.getObject(name);
        if (item == null) throw new NullPointerException(name);

        ItemStack itemStack = new ItemStack(item, count, meta);
        itemStack.setTagCompound(tag);
        return itemStack;
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
        if (id.charAt(0) == '_') //mymod.item.100 -> mymod.item._100
            id = id.substring(1);
        return changeNode(node.index(id));
    }

    @Override
    public Object withMeta(int meta) {
        return changeMeta(meta < anyMeta ? meta : anyMeta);
    }

    @Override
    public boolean equals(ItemStack inputItem) {
        return RecipeHelper.isEquals(this.getRecipeItem(), inputItem);
    }

    private ItemComponent changeNode(NamesTree.NameNode node) {
        if (node == null) return null;
        if (this.node.equals(node)) return this;
        return new ItemComponent(node, count, meta, tag);
    }

    private ItemComponent changeCount(int count) {
        if (this.count == count) return this;
        return new ItemComponent(node, count, meta, tag);
    }

    private ItemComponent changeMeta(int meta) {
        if (this.meta == meta) return this;
        return new ItemComponent(node, count, meta, tag);
    }

    private ItemComponent changeTag(NBTTagCompound tag) {
        if (this.tag.equals(tag)) return this;
        return new ItemComponent(node, count, meta, tag);
    }

    @Override
    public String toString() {
        String nl = System.lineSeparator();
        StringBuilder sb = new StringBuilder(67);
        sb.append(node.toString()).append(nl)
                .append("Count: ").append(count).append(nl)
                .append("Metadata: ").append(meta == anyMeta ? "any" : meta).append(nl)
                .append("Tag: ").append(tag);
        return sb.toString();
    }
}
