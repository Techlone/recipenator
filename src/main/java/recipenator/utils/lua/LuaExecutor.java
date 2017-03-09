package recipenator.utils.lua;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;
import recipenator.api.ILuaLibGetter;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class LuaExecutor {
    public static final String SCRIPT_EXT = ".lua";

    private final File directory;
    private final Globals globals;
    private final String relativePath;
    private List<String> names;

    public LuaExecutor(File directory, ILuaLibGetter environment) {
        this(directory);
        environment.getLibs().forEach(globals::load);
    }

    public LuaExecutor(File directory) {
        validateDirectory(directory);
        this.directory = directory;
        relativePath = getRelativePath();
        System.setProperty("luaj.package.path", String.format("%s?%s", relativePath, SCRIPT_EXT));
        loadScriptNames();
        globals = JsePlatform.standardGlobals();
    }

    private String getRelativePath() {
        Path userDirPath = Paths.get(System.getProperty("user.dir"));
        Path scriptDirPath = Paths.get(directory.getAbsolutePath());
        return userDirPath.relativize(scriptDirPath).toString() + File.separator;
    }

    public void loadScriptNames() {
        names = Arrays.asList(directory.list((d, n) -> n.endsWith(SCRIPT_EXT)));
    }

    public void execute() {
        names.forEach(this::execute);
    }

    public void execute(String name) {
        System.out.println("Execute: " + name);
        globals.loadfile(relativePath + name).call();
    }

    public void executeFile(String name) {
        execute(name + SCRIPT_EXT);
    }

    private static void validateDirectory(File dir) {
        if (dir == null)
            throw new NullPointerException("dir");
        if (!dir.exists() && !dir.mkdirs())
            throw new AssertionError(String.format("Can't make dir '%s'", dir.getPath()));
    }
}
