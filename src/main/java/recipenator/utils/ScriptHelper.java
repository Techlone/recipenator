package recipenator.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ScriptHelper {
    public static final String SCRIPT_EXT = ".lua";
    public static final String SCRIPT_FOLDER_NAME = "recipes";

    public static File getDefaultDirectory() {
        return new File(SCRIPT_FOLDER_NAME);
    }

    public static List<String> getScriptsNames(File directory) {
        if (directory == null) directory = getDefaultDirectory();
        List<String> result = new ArrayList<>();
        String[] list = directory.list();
        if (list == null) return result;
        for (String name : list)
            if (name.endsWith(SCRIPT_EXT))
                result.add(name);
        return result;
    }
}
