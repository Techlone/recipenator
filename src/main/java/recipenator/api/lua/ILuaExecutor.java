package recipenator.api.lua;

public interface ILuaExecutor {
    void executeAll();
    void executeByName(String name);

    void cancelAll();
    void cancelByName(String name);
}
