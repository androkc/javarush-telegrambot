package com.github.javarushcommunity.jrtb.command;

import com.github.javarushcommunity.jrtb.command.annotation.AdminCommand;
import com.github.javarushcommunity.jrtb.javarushclient.JavaRushGroupClient;
import com.github.javarushcommunity.jrtb.service.GroupSubService;
import com.github.javarushcommunity.jrtb.service.SendBotMessageService;
import com.github.javarushcommunity.jrtb.service.StatisticService;
import com.github.javarushcommunity.jrtb.service.TelegramUserService;
import com.google.common.collect.ImmutableMap;

import java.util.List;

import static com.github.javarushcommunity.jrtb.command.CommandNameEnum.*;
import static java.util.Objects.nonNull;

public class CommandContainer {
    private final ImmutableMap<String, Command> commandImmutableMap;
    private final Command unknownCommand;
    private final List<String> admins;

    public CommandContainer(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService
            , JavaRushGroupClient javaRushGroupClient, GroupSubService groupSubService, List<String> admins, StatisticService statisticService) {
        this.admins = admins;

        commandImmutableMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService, telegramUserService))
                .put(STOP.getCommandName(), new StopCommand(sendBotMessageService, telegramUserService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService))
                .put(STAT.getCommandName(), new StatCommand(sendBotMessageService,statisticService))
                .put(ADD_GROUP_SUB.getCommandName(), new AddGroupSubCommand(groupSubService, sendBotMessageService, javaRushGroupClient))
                .put(LIST_GROUP_SUB.getCommandName(), new ListGroupSubCommand(sendBotMessageService, telegramUserService))
                .put(DELETE_GROUP_SUB.getCommandName(), new DeleteGroupSubCommand(sendBotMessageService, telegramUserService, groupSubService))
                .put(ADMIN_HELP.getCommandName(), new AdminHelpCommand(sendBotMessageService))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command findCommand(String commandIdentifier, String username) {
        Command orDefault = commandImmutableMap.getOrDefault(commandIdentifier, unknownCommand);
        if (isAdminCommand(orDefault)) {
            if (admins.contains(username)) {
                return orDefault;
            } else {
                return unknownCommand;
            }
        }
        return orDefault;
    }

    private boolean isAdminCommand(Command command) {
        return nonNull(command.getClass().getAnnotation(AdminCommand.class));
    }
}
