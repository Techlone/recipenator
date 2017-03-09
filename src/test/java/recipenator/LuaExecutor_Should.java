package recipenator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import recipenator.utils.lua.LuaExecutor;

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

    @Before
    public void before() {
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
    public void ExecuteScript_GlobalTest() {
        executor.executeFile("global_test");
        String output = outContent.toString();
        Assert.assertEquals("123", output.substring(output.length() - 3));
    }

    @Test
    public void ExecuteScript_Items() {
        executor.executeFile("items");
    }

    @Test
    public void ExecuteScript_Recipe() {
        executor.executeFile("recipe");
    }
}
