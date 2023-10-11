package com.github.javarushcommunity.jrtb.command;

import com.github.javarushcommunity.jrtb.javarushclient.JavaRushGroupClient;
import com.github.javarushcommunity.jrtb.service.GroupSubService;
import com.github.javarushcommunity.jrtb.service.SendBotMessageService;
import com.github.javarushcommunity.jrtb.service.StatisticService;
import com.github.javarushcommunity.jrtb.service.TelegramUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static java.util.Collections.singletonList;

class CommandContainerTest {
    private CommandContainer commandContainer;

    @BeforeEach
    public void init() {
        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        JavaRushGroupClient javaRushGroupClient = Mockito.mock(JavaRushGroupClient.class);
        GroupSubService groupSubService = Mockito.mock(GroupSubService.class);
        StatisticService statisticService = Mockito.mock(StatisticService.class);
        commandContainer = new CommandContainer(sendBotMessageService, telegramUserService,
                javaRushGroupClient, groupSubService, singletonList("username"), statisticService);
    }

    @Test
    public void shouldGetAllTheExistingCommands() {
        Arrays.stream(CommandNameEnum.values())
                .forEach(commandNameEnum -> {
                    Command command = commandContainer.findCommand(commandNameEnum.getCommandName(), "username");
                    Assertions.assertNotEquals(UnknownCommand.class, command.getClass());
                });
    }

    @Test
    public void shouldReturnUnknownCommand() {
        String unknownCommand = "/asdasdasd";

        Command command = commandContainer.findCommand(unknownCommand, "username");

        Assertions.assertEquals(UnknownCommand.class, command.getClass());
    }

}