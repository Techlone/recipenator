package recipenator;

import recipenator.api.lua.ILuaLibGetter;
import recipenator.api.lua.LuaLibBase;
import recipenator.lualibs.FurnaceLib;
import recipenator.lualibs.ItemsLib;
import recipenator.lualibs.OreDictLib;
import recipenator.lualibs.RecipesLib;

import java.util.HashSet;
import java.util.Set;

public class DefaultLuaLibGetter implements ILuaLibGetter {
    @Override
    public Set<LuaLibBase> get() {
        return new HashSet<LuaLibBase>() {{
            add(new ItemsLib());
            add(new OreDictLib());
            add(new RecipesLib());
            add(new FurnaceLib());
        }};
    }
}
