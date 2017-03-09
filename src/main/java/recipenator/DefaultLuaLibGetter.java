package recipenator;

import net.minecraft.item.Item;
import recipenator.api.ILuaLibGetter;
import recipenator.lualib.BaseLuaLib;
import recipenator.lualib.item.ItemLib;
import recipenator.lualib.oredict.OreDictLib;
import recipenator.lualib.recipe.RecipeLib;
import recipenator.utils.NamesTree;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DefaultLuaLibGetter implements ILuaLibGetter {
    @Override
    public Set<BaseLuaLib> getLibs() {
        Set<BaseLuaLib> libs = new HashSet<>();
        Collection names = Item.itemRegistry.getKeys();
        libs.add(new ItemLib(NamesTree.getTreeRoot(names)));
        libs.add(new OreDictLib());
        libs.add(new RecipeLib());
        return libs;
    }
}
