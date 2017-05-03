package recipenator;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import recipenator.api.BaseRnSubCommand;
import recipenator.command.HelpCommand;
import recipenator.command.ReloadCommand;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RecipenatorCommand extends CommandBase {
    public static final String COMMAND_NAME = "recipenator";
    public static final String COMMAND_ALIAS = "rn";
    public static final String DEFAULT_SUBCOMMAND_NAME = "help";

    private static final Map<String, BaseRnSubCommand> commands = new HashMap<>();

    static {
        addCommand(new HelpCommand());
        addCommand(new ReloadCommand());
    }

    public static int getCommandCount() {
        return commands.size();
    }

    public static void addCommand(BaseRnSubCommand command) {
        commands.put(command.getName(), command);
    }

    public static void foreachCommands(Consumer<BaseRnSubCommand> action) {
        commands.values().forEach(action);
    }

    public static <T> List<T> mapCommands(Function<BaseRnSubCommand, T> selector) {
        return commands.values().stream().map(selector).collect(Collectors.toList());
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public String getCommandUsage(ICommandSender commandSender) {
        return "commands." + COMMAND_NAME + ".usage";
    }

    @Override
    public List getCommandAliases() {
        return Collections.singletonList(COMMAND_ALIAS);
    }

    @Override
    public void processCommand(ICommandSender commandSender, String[] arguments) {
        if (arguments.length == 0)
            commands.get(DEFAULT_SUBCOMMAND_NAME).execute(commandSender);
        else if (commands.containsKey(arguments[0]))
            commands.get(arguments[0]).execute(commandSender, Arrays.copyOfRange(arguments, 1, arguments.length));
        else
            commandSender.addChatMessage(new ChatComponentText("Unknown command: " + arguments[0]));

    }

    @Override
    public List addTabCompletionOptions(ICommandSender commandSender, String[] arguments) {
        if (arguments.length == 1)
            return getListOfStringsMatchingLastWord(arguments, (String[]) commands.keySet().toArray());
        if (arguments.length > 1 && commands.containsKey(arguments[0])) {
            String[] cases = (String[]) commands.get(arguments[0]).getAutocomplete().toArray();
            return getListOfStringsMatchingLastWord(Arrays.copyOfRange(arguments, 1, arguments.length), cases);
        }
        return null;
    }
}
