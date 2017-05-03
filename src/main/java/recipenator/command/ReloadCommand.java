package recipenator.command;

import net.minecraft.command.ICommandSender;
import recipenator.RecipenatorMod;
import recipenator.api.BaseRnSubCommand;
import recipenator.lualib.recipe.RecipeManager;
import recipenator.utils.ScriptHelper;
import recipenator.utils.lua.LuaExecutor;

import java.util.List;

public class ReloadCommand extends BaseRnSubCommand {
    @Override
    public void execute(ICommandSender commandSender, String... arguments) {
        RecipeManager.instance.removeAll();
        LuaExecutor luaExecutor = RecipenatorMod.getLuaExecutor();
        if (arguments.length > 0)
            luaExecutor.executeByName(arguments[0]);
        else
            luaExecutor.executeAll();
    }

    @Override
    public List<String> getAutocomplete() {
        return ScriptHelper.getScriptsNames(null);
    }
}
