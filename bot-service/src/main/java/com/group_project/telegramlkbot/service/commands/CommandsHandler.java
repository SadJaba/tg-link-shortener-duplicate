package com.group_project.telegramlkbot.service.commands;

import com.group_project.telegramlkbot.config.BotConfig;
import com.group_project.telegramlkbot.handlers.LinkDAO;
import com.group_project.telegramlkbot.handlers.UserDAO;
import com.group_project.telegramlkbot.utils.Consts;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component
public class CommandsHandler {

    private final Map<String, Command> commands;

    // public CommandsHandler(@Autowired StartCommand startCommand,
    // @Autowired AddLinkCommand addLinkCommand) {
    //
    // commands = Map.of(
    // "/start", startCommand,
    // "/add", addLinkCommand
    // );
    // }

    public CommandsHandler(UserDAO userDAO, LinkDAO linkDAO, BotConfig config) {
        StartCommand startCommand = new StartCommand(userDAO);
        AddLinkCommand addLinkCommand = new AddLinkCommand(linkDAO, config);
        ShowLinkCommand showLinkCommand = new ShowLinkCommand(linkDAO, config);
        ShowTableCommand showTableCommand = new ShowTableCommand(linkDAO, config);
        DeleteLinkCommand deleteLinkCommand = new DeleteLinkCommand(linkDAO);
        commands = Map.of(
                "/start", startCommand,
                "/add", addLinkCommand,
                "/show", showLinkCommand,
                "/table", showTableCommand,
                "/delete", deleteLinkCommand);
    }

    public SendMessage handleCommands(Update update) {
        String messageText = update.getMessage().getText();
        String command = messageText.split(" ")[0];
        long chatId = update.getMessage().getChatId();

        var commandHandler = commands.get(command);
        if (commandHandler != null) {
            return commandHandler.apply(update);
        } else {
            return new SendMessage(String.valueOf(chatId), Consts.UNKNOWN_COMMAND);
        }

    }
}
