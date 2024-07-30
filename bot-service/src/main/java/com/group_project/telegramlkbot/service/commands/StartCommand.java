package com.group_project.telegramlkbot.service.commands;

import com.group_project.telegramlkbot.data.UserEntity;
import com.group_project.telegramlkbot.handlers.UserDAO;
import com.group_project.telegramlkbot.utils.Consts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
@Component
public class StartCommand implements Command {

    private final UserDAO userDAO;

    public void register(long chatId) {
        UserEntity user = new UserEntity();
        user.setId(chatId);
        userDAO.addUser(user);
    }

    public SendMessage apply(Update update) {
        long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        register(chatId);
        sendMessage.setText(Consts.START_MESSAGE);
        return sendMessage;
    }
}
