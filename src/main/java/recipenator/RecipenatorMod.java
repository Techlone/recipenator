package recipenator;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.discovery.ASMDataTable;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.oredict.OreDictionary;
import org.luaj.vm2.lib.jse.JavaClassExtender;
import recipenator.api.ICancelable;
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

    public static void addCancelAction(ICancelable action) {
        luaExecutor.addCancelAction(action);
    }

    @EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        setLuaExecutor(new LuaExecutor(ScriptHelper.getDefaultDirectory(), new DefaultLuaLibGetter()));

        for (ASMDataTable.ASMData asmData : event.getAsmData().getAll(ClassExtension.class.getName())) {
            JavaClassExtender.extendBy(CommonHelper.ignoreErrors(Class::forName, asmData.getClassName(), null));
        }
    }

    @EventHandler
    public void onInit(FMLInitializationEvent event) {
        if (!event.getSide().isClient()) return;

        MinecraftForge.EVENT_BUS.register(new Object(){
            @SubscribeEvent
            public void onItemTooltip(ItemTooltipEvent event) {
                for (int i : OreDictionary.getOreIDs(event.itemStack)) {
                    event.toolTip.add("§8§o" + OreDictionary.getOreName(i));
                }
            }
        });
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
