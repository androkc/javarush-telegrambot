package com.github.javarushcommunity.jrtb.bot;

import com.github.javarushcommunity.jrtb.command.CommandContainer;
import com.github.javarushcommunity.jrtb.javarushclient.JavaRushGroupClient;
import com.github.javarushcommunity.jrtb.service.GroupSubService;
import com.github.javarushcommunity.jrtb.service.SendBotMessageServiceImpl;
import com.github.javarushcommunity.jrtb.service.StatisticService;
import com.github.javarushcommunity.jrtb.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;


import java.util.List;

import static com.github.javarushcommunity.jrtb.command.CommandNameEnum.*;


/**
 * Telegrambot for Javarush Community from Javarush community.
 */
@Component
public class JavarushTelegramBot extends TelegramLongPollingBot {
    public static String COMMAND_PREFIX = "/";
    @Value("${bot.username}")
    private String username;
    @Value("${bot.token}")
    private String token;
    private final CommandContainer commandContainer;

    public JavarushTelegramBot(TelegramUserService telegramUserService, JavaRushGroupClient javaRushGroupClient,
                               GroupSubService groupSubService, @Value("#{'${bot.admins}'.split(',')}") List<String> admins, StatisticService statisticService) {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this), telegramUserService,
                javaRushGroupClient, groupSubService,
                admins,statisticService);
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            String userName = update.getMessage().getFrom().getUserName();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();

                commandContainer.retrieveCommand(commandIdentifier, userName).execute(update);
            } else {
                commandContainer.retrieveCommand(NO.getCommandName(), userName).execute(update);
            }
        }

    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
