package recipenator.lualibs;

import net.minecraft.item.Item;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import recipenator.api.lua.LuaLibBase;
import recipenator.components.AnyComponent;
import recipenator.components.ItemComponent;
import recipenator.components.NullComponent;
import recipenator.utils.NamesTree;

public class ItemsLib extends LuaLibBase<ItemComponent> {
    private final NamesTree.NameNode root;

    public ItemsLib() {
        this(NamesTree.getTreeRoot(Item.itemRegistry.getKeys()));
    }

    public ItemsLib(NamesTree.NameNode root) {
        this.root = root;
    }

    @Override
    public LuaValue call(LuaValue modname, LuaValue env) {
        env.set("null", CoerceJavaToLua.coerce(NullComponent.INSTANCE));
        env.set("any", CoerceJavaToLua.coerce(AnyComponent.INSTANCE));
        return super.call(modname, env);
    }

    @Override
    public ItemComponent get() {
        return new ItemComponent(root);
    }
}
