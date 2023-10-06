package com.github.javarushcommunity.jrtb.command;

import com.github.javarushcommunity.jrtb.repository.entity.TelegramUser;
import com.github.javarushcommunity.jrtb.service.SendBotMessageService;
import com.github.javarushcommunity.jrtb.service.TelegramUserService;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.NotFoundException;

import java.util.stream.Collectors;

import static com.github.javarushcommunity.jrtb.command.CommandUtils.getChatId;

@RequiredArgsConstructor
public class ListGroupSubCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    @Override
    public void execute(Update update) {
        TelegramUser telegramUser = telegramUserService.findByChatId(getChatId(update).toString())
                .orElseThrow(NotFoundException::new);
        String message = "Я нашел все подписки на группы: \n\n";
        String collectGroups = telegramUser.getGroupSubs().stream()
                .map(it -> "Группа: " + it.getTitle() + ", ID = " + it.getId() + " \n")
                .collect(Collectors.joining());
        sendBotMessageService.sendMessage(telegramUser.getChatId(), message + collectGroups);
    }
}
