package recipenator;

import recipenator.api.ILuaLibGetter;
import recipenator.lualib.BaseLuaLib;
import recipenator.lualib.item.ItemLib;
import recipenator.lualib.oredict.OreDictLib;
import recipenator.utils.NamesTree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class TestLuaLibGetter implements ILuaLibGetter {
    @Override
    public Set<BaseLuaLib> getLibs() {
        Set<BaseLuaLib> libs = new HashSet<>();
        Collection<String> names;
        try {
            names = Files.readAllLines(Paths.get(".\\src\\test\\names.txt"));
        } catch (IOException ignored) {
            throw new RuntimeException("All is gone!");
        }
        //noinspection unchecked
        libs.add(new ItemLib(NamesTree.getTreeRoot(names)));
        libs.add(new OreDictLib());
        libs.add(new TestRecipeLib());
        return libs;
    }
}
