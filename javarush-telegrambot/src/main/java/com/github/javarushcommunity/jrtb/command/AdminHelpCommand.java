package com.github.javarushcommunity.jrtb.command;

import com.github.javarushcommunity.jrtb.service.SendBotMessageService;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.javarushcommunity.jrtb.command.CommandNameEnum.STAT;
import static java.lang.String.format;

@RequiredArgsConstructor
public class AdminHelpCommand implements Command {
    public static final String ADMIN_HELP_MESSAGE = format("✨<b>Доступные команды админа</b>✨\n\n"
                    + "<b>Получить статистику</b>\n"
                    + "%s - статистика бота\n",
            STAT.getCommandName());
    private final SendBotMessageService sendBotMessageService;

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        sendBotMessageService.sendMessage(chatId, ADMIN_HELP_MESSAGE);
    }
}
