package recipenator;

import codechicken.lib.packet.PacketCustom;
import codechicken.nei.NEICPH;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.discovery.ASMDataTable;
import cpw.mods.fml.common.event.*;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraftforge.common.MinecraftForge;
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
        PacketCustom.assignHandler(NEICPH.channel, new NEICPH() {
            @Override
            public void handlePacket(PacketCustom packetCustom, Minecraft minecraft, INetHandlerPlayClient iNetHandlerPlayClient) {
                super.handlePacket(packetCustom, minecraft, iNetHandlerPlayClient);
                if (packetCustom.getType() == 1) {
                    getLuaExecutor().executeAll();
                }
            }
        });
        MinecraftForge.EVENT_BUS.register(new ForgeEventHandler());
    }

    @EventHandler
    public void beforeServerStart(FMLServerAboutToStartEvent event) {
        //getLuaExecutor().executeAll();
    }

    @EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new RecipenatorCommand());
    }

    @EventHandler
    public void onServerStopping(FMLServerStoppingEvent event) {
        getLuaExecutor().cancelAll();
    }
}
