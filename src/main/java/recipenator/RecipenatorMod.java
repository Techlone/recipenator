package recipenator;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.discovery.ASMDataTable;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import recipenator.api.extention.ClassExtender;
import recipenator.api.extention.ClassExtension;
import recipenator.utils.CommonHelper;
import recipenator.utils.ScriptHelper;
import recipenator.utils.lua.LuaExecutor;

@Mod(modid = "", useMetadata = true)
public class RecipenatorMod {
    @EventHandler
    public void beforeServerStart(FMLServerAboutToStartEvent event) {
        getLuaExecutor().executeAll();
    }

    @EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new RecipenatorCommand());
    }

    @EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        for (ASMDataTable.ASMData asmData : event.getAsmData().getAll(ClassExtension.class.getName())) {
            CommonHelper.ignoreErrors(o -> ClassExtender.extendBy(Class.forName(o)), asmData.getClassName());
        }
    }

    public static LuaExecutor getLuaExecutor() {
        return new LuaExecutor(ScriptHelper.getDefaultDirectory(), new DefaultLuaLibGetter());
    }
}
