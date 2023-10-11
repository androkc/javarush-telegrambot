package com.github.javarushcommunity.jrtb.command;

import com.github.javarushcommunity.jrtb.service.SendBotMessageService;
import com.github.javarushcommunity.jrtb.service.TelegramUserService;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@AllArgsConstructor
public class StopCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    public static final String STOP_MESSAGE = "Деактивировал все ваши подписки \uD83D\uDE1F.";


    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();

        sendBotMessageService.sendMessage(chatId, STOP_MESSAGE);
        telegramUserService.findByChatId(chatId)
                .ifPresent(telegramUser -> {
                    telegramUser.setActive(false);
                    telegramUserService.save(telegramUser);
                });
    }
}
