package recipenator.api;

import recipenator.lualib.BaseLuaLib;

import java.util.Set;

public interface ILuaLibGetter {
    Set<BaseLuaLib> getLibs();
}
