package com.group_project.telegramlkbot.service.commands;

import com.group_project.telegramlkbot.data.LinkEntity;
import com.group_project.telegramlkbot.handlers.FullURLMatch;
import com.group_project.telegramlkbot.handlers.LinkDAO;
import com.group_project.telegramlkbot.utils.Consts;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
@RequiredArgsConstructor
public class DeleteLinkCommand implements Command {
    private final LinkDAO linkDAO;


    public LinkEntity findShortenLink(String shortenUrl) {
        List<LinkEntity> link = linkDAO.findShortenLink(shortenUrl);
        if (link.isEmpty()) {
            return null;
        }
        return link.get(0);
    }

    public String deleteLink(String shortenUrl) {
        LinkEntity link = findShortenLink(shortenUrl);
        if (link != null) {
            linkDAO.deleteLink(link);
            return Consts.DELETED_SUCCESSFULLY;
        } else {
            return Consts.LINK_NOT_FOUND_MESSAGE;
        }
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
            if (FullURLMatch.match(url)) {
                sendMessage.setText(Consts.INVALID_SOURCE_LINK);
                return sendMessage;
            }
            String result = deleteLink(url);
            sendMessage.setText(result);
            return sendMessage;
        }
    }
}
