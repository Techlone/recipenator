package recipenator;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChatComponentText;
import recipenator.api.ILuaLibGetter;
import recipenator.lualib.recipe.RecipeManager;
import recipenator.utils.lua.LuaExecutor;

import java.io.File;
import java.util.Collections;
import java.util.List;

@Mod(modid = "", useMetadata = true)
public class RecipenatorMod {
    public static final String SCRIPT_FOLDER_NAME = "recipes";
    public static final ILuaLibGetter LUA_ENVIRONMENT = new DefaultLuaLibGetter();

    public RecipenatorMod() { }

    @EventHandler
    public void beforeServerStart(FMLServerAboutToStartEvent event) {
        ((CommandHandler) event.getServer().getCommandManager()).registerCommand(new ICommand() {
            @Override
            public int compareTo(Object o) {
                return 0;
            }

            @Override
            public String getCommandName() {
                return "recipenator";
            }

            @Override
            public String getCommandUsage(ICommandSender commandSender) {
                return "";
            }

            @Override
            public List getCommandAliases() {
                return Collections.singletonList("rn");
            }

            @Override
            public void processCommand(ICommandSender commandSender, String[] arguments) {
                if (arguments.length == 0) return;

                if (arguments[0].equals("reload")) {
                    RecipeManager.removeAll();
                    ExecuteScripts();
                }
            }

            @Override
            public boolean canCommandSenderUseCommand(ICommandSender commandSender) {
                return true;
            }

            @Override
            public List addTabCompletionOptions(ICommandSender commandSender, String[] arguments) {
                return null;
            }

            @Override
            public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
                return false;
            }
        });
        ExecuteScripts();
    }

    public static void ExecuteScripts() {
        new LuaExecutor(new File(SCRIPT_FOLDER_NAME), LUA_ENVIRONMENT).execute();
    }

    public static void logChat(String msg) {
        MinecraftServer server = MinecraftServer.getServer();
        assert server != null;

        ServerConfigurationManager cm = server.getConfigurationManager();
        assert cm != null;

        ChatComponentText text = new ChatComponentText(msg);
        cm.sendChatMsg(text);
    }
}
