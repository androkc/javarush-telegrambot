package com.github.javarushcommunity.jrtb.command;

import com.github.javarushcommunity.jrtb.repository.entity.GroupSub;
import com.github.javarushcommunity.jrtb.repository.entity.TelegramUser;
import com.github.javarushcommunity.jrtb.service.SendBotMessageService;
import com.github.javarushcommunity.jrtb.service.TelegramUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.NotFoundException;
import java.util.List;
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
        List<GroupSub> groupSubs = telegramUser.getGroupSubs();
        String message;
        if (CollectionUtils.isEmpty(groupSubs)) {
            message = "Пока нет подписок на группы. Чтобы добавить подписку напиши /add_group_sub";
        } else {
            String collectGroups = telegramUser.getGroupSubs().stream()
                    .map(it -> "Группа: " + it.getTitle() + ", ID = " + it.getId() + " \n")
                    .collect(Collectors.joining());
            message = String.format("Я нашел все подписки на группы: \n\n %s", collectGroups);
        }
        sendBotMessageService.sendMessage(telegramUser.getChatId(), message);
    }
}
