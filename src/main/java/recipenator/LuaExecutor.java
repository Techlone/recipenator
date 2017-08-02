package recipenator;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;
import recipenator.api.ICancelable;
import recipenator.api.lua.ILuaExecutor;
import recipenator.api.lua.ILuaLibGetter;
import recipenator.utils.ScriptHelper;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LuaExecutor implements ILuaExecutor {
    private final Globals globals;
    private final String relativePath;
    private List<String> scriptNames;
    private Map<String, List<ICancelable>> cancelActions;
    private String currentScriptName;

    public LuaExecutor(File directory, ILuaLibGetter environment) {
        this(directory);
        environment.get().forEach(globals::load);
    }

    public LuaExecutor(File directory) {
        validateDirectory(directory);
        relativePath = getRelativePath(directory);
        System.setProperty("luaj.package.path", String.format("%s?%s", relativePath, ScriptHelper.SCRIPT_EXT));
        scriptNames = ScriptHelper.getScriptNames(directory);
        globals = JsePlatform.standardGlobals();
    }

    @Override
    public List<String> getScriptNames() {
        return scriptNames;
    }

    @Override
    public void addCancelAction(ICancelable action) {
        if (this.currentScriptName == null) throw new NullPointerException();
        this.cancelActions.computeIfAbsent(currentScriptName, s -> new ArrayList<>()).add(action);
    }

    @Override
    public void executeAll() {
        scriptNames.forEach(this::executeByName);
        this.currentScriptName = null;
    }

    @Override
    public void executeByName(String name) {
        this.currentScriptName = name;
        String filename = relativePath + name;
        //TODO I need log
        System.out.println("Execute: " + filename);
        LuaValue loadedFile = null;
        try {
            loadedFile = globals.loadfile(filename);
        } catch (Exception e) {
            System.err.println("Can't load file: " + filename);
            printFullStackTrace(e);
            return;
        }

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
        scriptNames.forEach(this::cancelByName);
    }

    @Override
    public void cancelByName(String name) {
        List<ICancelable> cancelActions = this.cancelActions.get(name);
        if (cancelActions == null) return;
        cancelActions.forEach(ICancelable::cancel);
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
