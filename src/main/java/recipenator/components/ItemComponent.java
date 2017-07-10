package recipenator.components;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import recipenator.api.component.RecipeComponentBase;
import recipenator.api.component.IRecipeComponent;
import recipenator.utils.NamesTree;
import recipenator.utils.RecipeHelper;

import java.util.Collections;
import java.util.List;

public class ItemComponent extends RecipeComponentBase<ItemStack> {
    public static ItemComponent fromItemStack(ItemStack itemStack) {
        NamesTree.NameNode nameNode = new NamesTree.NameNode(itemStack.getUnlocalizedName());
        return new ItemComponent(nameNode, itemStack.stackSize, itemStack.getItemDamage(), itemStack.getTagCompound());
    }

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
        return changeNode(node.next(id));
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
    public List<ItemStack> getAllItems() {
        return Collections.singletonList(getRecipeItem());
    }

    public String getName() {
        return node.getName();
    }

    @Override
    public String toString() {
        String nl = System.lineSeparator();
        return node.toString() + nl +
                "Count: " + count + nl +
                "Metadata: " + (meta == RecipeComponentBase.anyMeta ? "any" : meta) + nl +
                "Tag: " + tag;
    }
}
