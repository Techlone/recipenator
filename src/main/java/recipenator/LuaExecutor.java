package recipenator;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;
import recipenator.api.lua.ILuaExecutor;
import recipenator.api.lua.ILuaLibGetter;
import recipenator.utils.ScriptHelper;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LuaExecutor implements ILuaExecutor {
    private final Globals globals;
    private final String relativePath;
    private List<String> scriptsNames;

    public LuaExecutor(File directory, ILuaLibGetter environment) {
        this(directory);
        environment.get().forEach(globals::load);
    }

    public LuaExecutor(File directory) {
        validateDirectory(directory);
        relativePath = getRelativePath(directory);
        System.setProperty("luaj.package.path", String.format("%s?%s", relativePath, ScriptHelper.SCRIPT_EXT));
        scriptsNames = ScriptHelper.getScriptsNames(directory);
        globals = JsePlatform.standardGlobals();
    }

    @Override
    public void executeAll() {
        scriptsNames.forEach(this::executeByName);
    }

    @Override
    public void executeByName(String name) {
        String filename = relativePath + name;
        //TODO I need log
        System.out.println("Execute: " + filename);
        LuaValue loadedFile = null;
        try {
            loadedFile = globals.loadfile(filename);
        } catch (Exception e) {
            System.err.println("Can't load file: " + filename);
            printFullStackTrace(e);
        }
        if (loadedFile == null) return;
        try {
            loadedFile.call();
        } catch (Exception e) {
            System.err.println("Can't execute file: " + filename);
            printFullStackTrace(e);
        }
    }

    private void printFullStackTrace(Throwable e) {
        Throwable thr = e;
        do {
            thr.printStackTrace();
            thr = thr.getCause();
        } while (thr != null);
    }

    @Override
    public void cancelAll() {

    }

    @Override
    public void cancelByName(String name) {

    }

    private static void validateDirectory(File dir) {
        if (dir == null)
            throw new NullPointerException("dir");
        if (!dir.exists() && !dir.mkdirs())
            throw new AssertionError(String.format("Can't make dir '%s'", dir.getPath()));
    }

    private static String getRelativePath(File directory) {
        Path userDirPath = Paths.get(System.getProperty("user.dir"));
        Path scriptDirPath = Paths.get(directory.getAbsolutePath());
        return userDirPath.relativize(scriptDirPath).toString() + File.separator;
    }
}
