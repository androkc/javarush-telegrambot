package com.github.javarushcommunity.jrtb.service;

import com.github.javarushcommunity.jrtb.bot.JavarushTelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * Implementation of {@link SendBotMessageService} interface.
 */
@Service
public class SendBotMessageServiceImpl implements SendBotMessageService {

    private final JavarushTelegramBot javarushBot;

    @Autowired
    public SendBotMessageServiceImpl(JavarushTelegramBot javarushBot) {
        this.javarushBot = javarushBot;
    }

    @Override
    public void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.enableHtml(true);
        sendMessage.setText(message);

        try {
            javarushBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(Long chatId, List<String> message) {
        if (isEmpty(message)) return;
        message.forEach(m -> sendMessage(chatId, m));
    }
}
