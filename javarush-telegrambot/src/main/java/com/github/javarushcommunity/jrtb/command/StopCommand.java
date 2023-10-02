package com.github.javarushcommunity.jrtb.command;

import com.github.javarushcommunity.jrtb.service.SendBotMessageService;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@AllArgsConstructor
public class StopCommand implements Command{
    private final SendBotMessageService sendBotMessageService;
    public static final String STOP_MESSAGE = "Деактивировал все ваши подписки \uD83D\uDE1F.";


    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(),STOP_MESSAGE);
    }
}
