package com.group_project.telegramlkbot.service.commands;

import com.group_project.telegramlkbot.config.BotConfig;
import com.group_project.telegramlkbot.data.LinkEntity;
import com.group_project.telegramlkbot.handlers.LinkDAO;
import com.group_project.telegramlkbot.utils.Consts;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class ShowTableCommand implements Command {
    private final LinkDAO linkDAO;
    private final BotConfig config;

    public ShowTableCommand(LinkDAO linkDAO, BotConfig config) {
        this.linkDAO = linkDAO;
        this.config = config;
    }

    public String getTable(long authorId) {
        List<LinkEntity> table = linkDAO.findTable(authorId);
        if (table.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (LinkEntity l : table) {
            sb.append("Короткая ссылка\uD83D\uDCAA: ").append(config.getShortUrl()).append(l.getShortenUrl())
                    .append("\nПолная ссылка\uD83E\uDD13: ")
                    .append(l.getSourceUrl()).append("\nКоличество переходов\uD83D\uDEB8: ")
                    .append(l.getRedirectCount()).append("\n\n");
        }
        return sb.toString();
    }

    public SendMessage apply(Update update) {
        long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));

        String table = getTable(chatId);
        if (table == null) {
            sendMessage.setText(Consts.TABLE_NOT_FOUND);
            return sendMessage;
        }
        String[] command = update.getMessage().getText().split(" +");
        if (command.length != 1) {
            sendMessage.setText(Consts.UNNECESSARY_PARAMETERS + table);
        } else {
            sendMessage.setText(table);
        }
        return sendMessage;
    }
}
