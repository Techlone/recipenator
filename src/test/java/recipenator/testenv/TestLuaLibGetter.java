package recipenator.testenv;

import recipenator.api.lua.ILuaLibGetter;
import recipenator.api.lua.LuaLibBase;
import recipenator.lualibs.ItemsLib;
import recipenator.lualibs.OreDictLib;
import recipenator.utils.NamesTree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class TestLuaLibGetter implements ILuaLibGetter {
    @Override
    public Set<LuaLibBase> get() {
        Set<LuaLibBase> libs = new HashSet<>();
        Collection<String> names;
        try {
            names = Files.readAllLines(Paths.get(".\\src\\test\\names.txt"));
        } catch (IOException ignored) {
            throw new RuntimeException("All is gone!");
        }

        libs.add(new ItemsLib(NamesTree.getTreeRoot(names)));
        libs.add(new OreDictLib());
        libs.add(new TestRecipesLib());
        return libs;
    }
}
