package recipenator.command;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import recipenator.RecipenatorCommand;
import recipenator.api.BaseRnSubCommand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static recipenator.utils.CommonHelper.ignoreErrors;

public class HelpCommand extends BaseRnSubCommand {
    private static final int COMMANDS_PER_PAGE = 5;

    public static int getMaxPage(int size) {
        return (size - 1) / COMMANDS_PER_PAGE;
    }

    @Override
    public void execute(ICommandSender commandSender, String... arguments) {
        List<String> messages = RecipenatorCommand.mapCommands(c -> "/rn " + c.getName() + ": " + c.getDescription());
        Collections.sort(messages);

        int page = Math.min(ignoreErrors(() -> Integer.parseInt(arguments[0]) - 1, -1), getMaxPage(messages.size()));
        if (page > -1)
            messages = messages.subList(page * COMMANDS_PER_PAGE, Math.min((page + 1) * COMMANDS_PER_PAGE, messages.size()));

        commandSender.addChatMessage(new ChatComponentText(String.join(System.lineSeparator(), messages)));
    }

    @Override
    public List<String> getAutocomplete() {
        int maxPage = getMaxPage(RecipenatorCommand.getCommandCount());
        List<String> result = new ArrayList<>(maxPage);
        for (int i = 0; i < maxPage; i++) result.add(Integer.toString(i + 1));
        return result;
    }
}