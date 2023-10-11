package com.github.javarushcommunity.jrtb.service;

import com.github.javarushcommunity.jrtb.bot.JavarushTelegramBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit-level testing for SendBotMessageService")
class SendBotMessageServiceTest {
    private SendBotMessageService sendBotMessageService;
    private JavarushTelegramBot javarushTelegramBot;

    @BeforeEach
    public void init() {
        javarushTelegramBot = Mockito.mock(JavarushTelegramBot.class);
        sendBotMessageService = new SendBotMessageServiceImpl(javarushTelegramBot);
    }

    @Test
    public void shouldProperlySendMessage() throws TelegramApiException {
        Long chatId = 1L;
        String message = "test_message";

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.enableHtml(true);
        sendMessage.setText(message);

        sendBotMessageService.sendMessage(chatId, message);

        Mockito.verify(javarushTelegramBot).execute(sendMessage);
    }
}