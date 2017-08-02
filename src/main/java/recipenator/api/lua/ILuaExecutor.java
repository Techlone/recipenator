package recipenator.api.lua;

import recipenator.api.ICancelable;

import java.util.List;

public interface ILuaExecutor {
    void addCancelAction(ICancelable action);

    void executeAll();
    void executeByName(String name);

    void cancelAll();
    void cancelByName(String name);

    List<String> getScriptNames();
}
