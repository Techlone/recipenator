package recipenator.lualib.item;

import org.luaj.vm2.LuaValue;
import recipenator.lualib.BaseLuaLib;
import recipenator.utils.NamesTree;
import recipenator.utils.lua.MetamethodWrapper;

public class ItemLib extends BaseLuaLib {
    private final NamesTree.NameNode root;

    public ItemLib(NamesTree.NameNode root) {
        this.root = root;
    }

    @Override
    public LuaValue call(LuaValue modname, LuaValue env) {
        env.set("null", MetamethodWrapper.wrap(NullComponent.NULL));
        return super.call(modname, env);
    }

    @Override
    protected String getLibName() {
        return "item";
    }

    @Override
    protected Object getLib() {
        return new ItemComponent(root);
    }
}
