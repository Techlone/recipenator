package recipenator.testenv;

import recipenator.api.lua.ILuaLibGetter;
import recipenator.api.lua.LuaLibBase;
import recipenator.lualibs.*;
import recipenator.utils.NamesTree;
import recipenator.utils.OreDictIndexer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class TestLuaLibGetter implements ILuaLibGetter {
    @Override
    public Set<LuaLibBase> get() {
        try {
            Collection<String> names = Files.readAllLines(Paths.get(".\\src\\test\\names.txt"));
            return new HashSet<LuaLibBase>() {{
                add(new ItemsLib(NamesTree.getTreeRoot(names)));
                add(new OreDictLib() {
                    @Override
                    public OreDictIndexer get() {
                        return new TestOreDictIndexer();
                    }
                });
                add(new RecipesLib());
                add(new FurnaceLib());
                add(new NbtLib());
            }};
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("All is gone!");
        }
    }
}
