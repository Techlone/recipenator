package recipenator;

import net.minecraft.item.Item;
import recipenator.api.lua.ILuaLibGetter;
import recipenator.api.lua.BaseLuaLib;
import recipenator.lualib.item.ItemLib;
import recipenator.lualib.oredict.OreDictLib;
import recipenator.lualib.recipe.RecipeLib;
import recipenator.utils.NamesTree;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DefaultLuaLibGetter implements ILuaLibGetter {
    @Override
    public Set<BaseLuaLib> get() {
        Set<BaseLuaLib> libs = new HashSet<>();
        libs.add(new ItemLib());
        libs.add(new OreDictLib());
        libs.add(new RecipeLib());
        return libs;
    }
}
