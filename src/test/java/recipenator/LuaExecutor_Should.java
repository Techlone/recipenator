package recipenator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.luaj.vm2.lib.jse.JavaClassExtender;
import recipenator.testenv.TestItemExtension;
import recipenator.testenv.TestLuaLibGetter;
import recipenator.utils.ScriptHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

public class LuaExecutor_Should {
    private static final String TEST_DIR_RECIPES = "src/test/lua/recipes";

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    private PrintStream outDefault;
    private PrintStream errDefault;
    private LuaExecutor executor;

    public void execute(String name) {
        executor.executeByName(name + ScriptHelper.SCRIPT_EXT);
    }

    @Before
    public void before() {
        JavaClassExtender.extendBy(TestItemExtension.class);

        outDefault = System.out;
        errDefault = System.err;
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        executor = new LuaExecutor(new File(TEST_DIR_RECIPES), new TestLuaLibGetter());
    }

    @After
    public void after() {
        System.setOut(outDefault);
        System.setErr(errDefault);
        System.out.println(outContent.toString());
        System.err.println(errContent.toString());
    }

    @Test
    public void ExecuteScript_Items() {
        execute("items");
    }

    @Test
    public void ExecuteScript_Recipe() {
        execute("recipe");
    }

    @Test
    public void ExecuteScript_ItemExt() {
        execute("itemext");
    }

    @Test
    public void ExecuteScript_FieldWrap() {
        execute("field_wrap");
    }

    @Test
    public void ExecuteScript_Furnace() {
        execute("furnace");
    }

    @Test
    public void ExecuteScript_Ore() {
        execute("ore");
    }

    @Test
    public void ExecuteScript_Tag() {
        execute("tag");
    }
}
