package recipenator.api;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.StatCollector;
import recipenator.RecipenatorCommand;

import java.util.List;

public abstract class BaseRnSubCommand {
    // 'command' length
    private static final int TILE_LENGTH = 7;

    private String name;
    private String translatingKey;

    protected BaseRnSubCommand() {
        String className = this.getClass().getName();
        name = className.substring(0, className.length() - TILE_LENGTH);
        translatingKey = "commands." + RecipenatorCommand.COMMAND_NAME + "." + name + ".usage";
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return StatCollector.translateToLocal(translatingKey);
    }

    public abstract void execute(ICommandSender commandSender, String... arguments);

    public List<String> getAutocomplete() {
        return null;
    }
}
