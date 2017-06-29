package recipenator;

import recipenator.api.lua.ILuaLibGetter;
import recipenator.api.lua.LuaLibBase;
import recipenator.lualibs.ItemsLib;
import recipenator.lualibs.OreDictLib;
import recipenator.lualibs.RecipesLib;

import java.util.HashSet;
import java.util.Set;

public class DefaultLuaLibGetter implements ILuaLibGetter {
    @Override
    public Set<LuaLibBase> get() {
        Set<LuaLibBase> libs = new HashSet<>();
        libs.add(new ItemsLib());
        libs.add(new OreDictLib());
        libs.add(new RecipesLib());
        return libs;
    }
}
