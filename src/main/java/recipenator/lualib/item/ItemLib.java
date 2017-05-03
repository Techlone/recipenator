package recipenator.lualib.item;

import net.minecraft.item.Item;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import recipenator.api.lua.BaseLuaLib;
import recipenator.utils.NamesTree;

public class ItemLib extends BaseLuaLib<ItemComponent> {
    private final NamesTree.NameNode root;

    public ItemLib() {
        this(NamesTree.getTreeRoot(Item.itemRegistry.getKeys()));
    }

    public ItemLib(NamesTree.NameNode root) {
        this.root = root;
    }

    @Override
    public LuaValue call(LuaValue modname, LuaValue env) {
        env.set("null", CoerceJavaToLua.coerce(NullComponent.NULL));
        return super.call(modname, env);
    }

    @Override
    public String getName() {
        return "item";
    }

    @Override
    public ItemComponent get() {
        return new ItemComponent(root);
    }
}
