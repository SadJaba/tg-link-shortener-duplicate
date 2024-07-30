package com.group_project.telegramlkbot.service.commands;

import com.group_project.telegramlkbot.config.BotConfig;
import com.group_project.telegramlkbot.data.LinkEntity;
import com.group_project.telegramlkbot.handlers.FullURLMatch;
import com.group_project.telegramlkbot.handlers.GenerateShortUrl;
import com.group_project.telegramlkbot.handlers.LinkDAO;
import com.group_project.telegramlkbot.utils.Consts;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.OffsetDateTime;
import java.util.List;

@AllArgsConstructor
public class AddLinkCommand implements Command {

    private final LinkDAO linkDAO;
    private final BotConfig config;


    public void addNewLink(Long chat_id, String fullurl, String shortURL) {
//        UserEntity user = new UserEntity();
//        user.setId(chat_id);
//        userDto.addUser(user);
        LinkEntity link = new LinkEntity();
        link.setSourceUrl(fullurl);
        link.setShortenUrl(shortURL);
        link.setRedirectCount(0);
        link.setCreatedAt(OffsetDateTime.now());
        link.setUpdatedAt(OffsetDateTime.now());
        link.setAuthorId(chat_id);
        linkDAO.addLink(link);
    }

    @Override
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
            if (url.length() > 255){
                sendMessage.setText(Consts.LINK_OUT_OF_RANGE);
                return sendMessage;
            }else if (!FullURLMatch.match(url)) {
                sendMessage.setText(Consts.INVALID_LINK);
                return sendMessage;
            }else {
                String shortURL = GenerateShortUrl.generate();
                List<LinkEntity> link = linkDAO.findTable(chatId);
                if (link.size() >= 10) {
                    sendMessage.setText(Consts.TABLE_FULL);
                    return sendMessage;
                }else{
                    addNewLink(chatId, url, shortURL);
                    sendMessage.setText(Consts.LINK_ADDED + "\nВот ваша короткая ссылка: "  + config.getShortUrl()+ shortURL);
                    return sendMessage;
                }

            }
        }

    }
}
