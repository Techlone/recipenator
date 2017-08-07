package recipenator.commands;

import net.minecraft.command.ICommandSender;
import recipenator.RecipenatorMod;
import recipenator.api.SubCommandBase;
import recipenator.api.lua.ILuaExecutor;

import java.util.List;

public class ReloadCommand extends SubCommandBase {
    @Override
    public void execute(ICommandSender commandSender, String... arguments) {
        ILuaExecutor luaExecutor = RecipenatorMod.getLuaExecutor();
        if (arguments.length > 0) {
            luaExecutor.cancelByName(arguments[0]);
            luaExecutor.executeByName(arguments[0]);
        } else {
            luaExecutor.cancelAll();
            luaExecutor.executeAll();
        }
    }

    @Override
    public List<String> getAutocomplete() {
        return RecipenatorMod.getLuaExecutor().getScriptNames();
    }
}
