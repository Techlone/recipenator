package recipenator.lualib.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import recipenator.api.component.BaseRecipeComponent;
import recipenator.api.component.IRecipeComponent;
import recipenator.utils.NamesTree;
import recipenator.utils.RecipeHelper;

public class ItemComponent extends BaseRecipeComponent<ItemStack> {
    private final NamesTree.NameNode node;

    public ItemComponent(NamesTree.NameNode node) {
        this(node, 1, 0, null);
    }

    public ItemComponent(NamesTree.NameNode node, int count, int meta, NBTTagCompound tag) {
        super(count, meta, tag);
        this.node = node;
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
    public Object index(String id) {
        Object base = super.index(id);
        if (base != null) return base;

        if (id.charAt(0) == '_') //mymod.item._100 -> mymod.item.100
            id = id.substring(1);
        return changeNode(node.index(id));
    }

    private ItemComponent changeNode(NamesTree.NameNode node) {
        if (node == null) return null;
        if (this.node.equals(node)) return this;
        return new ItemComponent(node, count, meta, tag);
    }

    @Override
    protected IRecipeComponent<ItemStack> newInstance(int count, int meta, NBTTagCompound tag) {
        return new ItemComponent(node, count, meta, tag);
    }

    @Override
    public boolean equals(ItemStack inputItem) {
        return RecipeHelper.isEquals(this.getRecipeItem(), inputItem);
    }

    @Override
    public String toString() {
        String nl = System.lineSeparator();
        return node.toString() + nl +
                "Count: " + count + nl +
                "Metadata: " + (meta == BaseRecipeComponent.anyMeta ? "any" : meta) + nl +
                "Tag: " + tag;
    }
}
