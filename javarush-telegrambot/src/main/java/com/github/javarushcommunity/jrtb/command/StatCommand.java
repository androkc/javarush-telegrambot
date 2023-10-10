package com.github.javarushcommunity.jrtb.command;

import com.github.javarushcommunity.jrtb.command.annotation.AdminCommand;
import com.github.javarushcommunity.jrtb.dto.StatisticDTO;
import com.github.javarushcommunity.jrtb.service.SendBotMessageService;
import com.github.javarushcommunity.jrtb.service.StatisticService;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

@AllArgsConstructor
@AdminCommand
public class StatCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final StatisticService statisticService;
    public final static String STAT_MESSAGE = "✨<b>Подготовил статистику</b>✨\n" +
            "- Количество активных пользователей: %s\n" +
            "- Количество неактивных пользователей: %s\n" +
            "- Среднее количество групп на одного пользователя: %s\n\n" +
            "<b>Информация по активным группам</b>:\n" +
            "%s";

    @Override
    public void execute(Update update) {
        StatisticDTO statisticDTO = statisticService.countBotStatistic();

        String collected = statisticDTO.getGroupStatDTOList().stream()
                .map(it -> String.format("%s (id = %s) - %s подписчиков", it.getTitle(), it.getId(), it.getActiveUserCount()))
                .collect(Collectors.joining("\n"));


        String chatId = update.getMessage().getChatId().toString();
        sendBotMessageService.sendMessage(chatId, String.format(STAT_MESSAGE,
                statisticDTO.getActiveUserCount(),
                statisticDTO.getInactiveUserCount(),
                statisticDTO.getAverageGroupCountByUser(),
                collected));
    }
}
