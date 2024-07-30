package com.group_project.telegramlkbot.service.commands;

import com.group_project.telegramlkbot.config.BotConfig;
import com.group_project.telegramlkbot.data.LinkEntity;
import com.group_project.telegramlkbot.handlers.LinkDAO;
import com.group_project.telegramlkbot.utils.Consts;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
@RequiredArgsConstructor
public class ShowLinkCommand implements Command {
    private final LinkDAO linkDAO;
    private final BotConfig config;


    public String getLink(String sourceUrl) {
        List<LinkEntity> link = linkDAO.findLink(sourceUrl);

        if (link.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (LinkEntity l : link) {
            sb.append("Короткая ссылка\uD83D\uDCAA: ")
                    .append(config.getShortUrl()).append(l.getShortenUrl()).append("\n")
                    .append("Количество переходов\uD83D\uDEB8: ")
                    .append(l.getRedirectCount()).append("\n\n");
        }
        return sb.toString();
    }

    public SendMessage apply(Update update) {
        long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));

        String[] command = update.getMessage().getText().split(" +");
        if (command.length < 2) {
            sendMessage.setText(Consts.ABSENT_LINK);
            return sendMessage;
        } else {
            String url = command[1];
            String link = getLink(url);
            if (link == null) {
                sendMessage.setText(Consts.LINK_NOT_FOUND);
                return sendMessage;
            }
            sendMessage.setText(link);
            return sendMessage;
        }
    }
}
