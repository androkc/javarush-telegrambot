package com.github.javarushcommunity.jrtb.command;


import com.github.javarushcommunity.jrtb.dto.GroupStatDTO;
import com.github.javarushcommunity.jrtb.dto.StatisticDTO;
import com.github.javarushcommunity.jrtb.service.SendBotMessageService;
import com.github.javarushcommunity.jrtb.service.StatisticService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static com.github.javarushcommunity.jrtb.command.AbstractCommandTest.prepareUpdate;
import static com.github.javarushcommunity.jrtb.command.StatCommand.STAT_MESSAGE;

@DisplayName("Unit-level testing for StatCommand")
public class StatCommandTest {
    private SendBotMessageService sendBotMessageService;
    private StatisticService statisticService;
    private StatCommand command;

    @BeforeEach
    public void init() {
        sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        statisticService = Mockito.mock(StatisticService.class);

        command = new StatCommand(sendBotMessageService, statisticService);
    }

    @Test
    public void shouldProperlySendMessage() {
        Long chatId = 123123L;
        GroupStatDTO groupStatDTO = new GroupStatDTO(1, "g1", 1);
        StatisticDTO statisticDTO = new StatisticDTO(1, 1, Collections.singletonList(groupStatDTO), 3.5);

        Mockito.when(statisticService.countBotStatistic()).thenReturn(statisticDTO);

        command.execute(prepareUpdate(chatId, CommandNameEnum.STAT.getCommandName()));

        Mockito.verify(sendBotMessageService).sendMessage(chatId,String.format(STAT_MESSAGE,
                statisticDTO.getActiveUserCount(),
                statisticDTO.getInactiveUserCount(),
                statisticDTO.getAverageGroupCountByUser(),
                String.format("%s (id = %s) - %s подписчиков", groupStatDTO.getTitle(), groupStatDTO.getId(), groupStatDTO.getActiveUserCount())));
    }
}
