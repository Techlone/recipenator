package recipenator;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.discovery.ASMDataTable;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.init.Items;
import net.minecraftforge.oredict.OreDictionary;
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
        //OreDictionary.
        OreDictionary.registerOre("oreFurnace", Items.furnace_minecart);
    }

//    @EventHandler
//    public void onInit(FMLInitializationEvent event) {
//        if (event.getSide().isClient()) {
//            MinecraftForge.EVENT_BUS.register(new Object(){
//                @SubscribeEvent
//                public void onItemTooltip(ItemTooltipEvent event) {
//                    if(event.showAdvancedItemTooltips) {
//                        int[] var2 = OreDictionary.getOreIDs(event.itemStack);
//                        int var3 = var2.length;
//
//                        for(int var4 = 0; var4 < var3; ++var4) {
//                            int i = var2[var4];
//                            event.toolTip.add("§8§o" + OreDictionary.getOreName(i));
//                        }
//
//                    }
//                }
//            });
//        }
//    }

    @EventHandler
    public void beforeServerStart(FMLServerAboutToStartEvent event) {
        getLuaExecutor().executeAll();
    }

    @EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new RecipenatorCommand());
    }
}
