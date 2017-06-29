package recipenator;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.discovery.ASMDataTable;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import org.luaj.vm2.lib.jse.JavaClassExtender;
import recipenator.api.extention.ClassExtension;
import recipenator.api.lua.ILuaExecutor;
import recipenator.utils.CommonHelper;
import recipenator.utils.ScriptHelper;

@Mod(modid = "", useMetadata = true)
public class RecipenatorMod {
    private static ILuaExecutor luaExecutor = null;

    public static void setLuaExecutor(ILuaExecutor luaExecutor) {
        RecipenatorMod.luaExecutor = luaExecutor;
    }

    public static ILuaExecutor getLuaExecutor() {
        return luaExecutor;
    }

    @EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        setLuaExecutor(new LuaExecutor(ScriptHelper.getDefaultDirectory(), new DefaultLuaLibGetter()));

        for (ASMDataTable.ASMData asmData : event.getAsmData().getAll(ClassExtension.class.getName())) {
            JavaClassExtender.extendBy(CommonHelper.ignoreErrors(Class::forName, asmData.getClassName(), null));
        }
    }

    @EventHandler
    public void beforeServerStart(FMLServerAboutToStartEvent event) {
        getLuaExecutor().executeAll();
    }

    @EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new RecipenatorCommand());
    }
}
