package com.github.javarushcommunity.jrtb.command;

import com.github.javarushcommunity.jrtb.repository.entity.GroupSub;
import com.github.javarushcommunity.jrtb.repository.entity.TelegramUser;
import com.github.javarushcommunity.jrtb.service.GroupSubService;
import com.github.javarushcommunity.jrtb.service.SendBotMessageService;
import com.github.javarushcommunity.jrtb.service.TelegramUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static com.github.javarushcommunity.jrtb.command.AbstractCommandTest.*;
import static com.github.javarushcommunity.jrtb.command.CommandNameEnum.DELETE_GROUP_SUB;

@DisplayName("Unit-level testing for DeleteGroupSubCommand")
class DeleteGroupSubCommandTest {
    private Command command;
    private SendBotMessageService sendBotMessageService;
    TelegramUserService telegramUserService;
    GroupSubService groupSubService;

    @BeforeEach
    public void init() {
        sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        telegramUserService = Mockito.mock(TelegramUserService.class);
        groupSubService = Mockito.mock(GroupSubService.class);

        command = new DeleteGroupSubCommand(sendBotMessageService, telegramUserService, groupSubService);
    }

    @Test
    public void shouldProperlyReturnEmptySubscriptionList() {
        Long chatId = 123213L;

        Update update = prepareUpdate(chatId, DELETE_GROUP_SUB.getCommandName());

        Mockito.when(telegramUserService.findByChatId(chatId))
                .thenReturn(Optional.of(new TelegramUser()));

        String expected = "Пока нет подписок на группы. Чтобы добавить подписку напиши /add_group_sub";

        command.execute(update);

        Mockito.verify(sendBotMessageService).sendMessage(chatId, expected);
    }

    @Test
    public void shouldProperlyReturnSubscriptionLit() {
        Long chatId = 12314L;

        Update update = prepareUpdate(chatId, DELETE_GROUP_SUB.getCommandName());

        TelegramUser telegramUser = new TelegramUser();
        GroupSub groupSub = new GroupSub();
        groupSub.setId(123);
        groupSub.setTitle("g1");
        telegramUser.setGroupSubs(Collections.singletonList(groupSub));

        Mockito.when(telegramUserService.findByChatId(chatId)).thenReturn(Optional.of(telegramUser));

        String expected = "Чтобы удалить подписку на группу - передай комадну вместе с ID группы. \n" +
                "Например: /delete_group_sub 16 \n\n" +
                "я подготовил список всех групп, на которые ты подписан) \n\n" +
                "имя группы - ID группы \n\n" +
                "g1 - 123 \n";

        command.execute(update);

        Mockito.verify(sendBotMessageService).sendMessage(chatId, expected);
    }

    @Test
    public void shouldRejectByInvalidGroupId() {
        Long chatId = 1231L;

        Update update = prepareUpdate(chatId, String.format("%s %s", DELETE_GROUP_SUB.getCommandName(), "groupSubId"));

        TelegramUser telegramUser = new TelegramUser();
        GroupSub groupSub = new GroupSub();
        groupSub.setTitle("g1");
        groupSub.setId(123);
        telegramUser.setGroupSubs(Collections.singletonList(groupSub));
        Mockito.when(telegramUserService.findByChatId(chatId)).thenReturn(Optional.of(telegramUser));

        String expected = "неправильный формат ID группы.\n " +
                "ID должно быть целым положительным числом";

        command.execute(update);

        Mockito.verify(sendBotMessageService).sendMessage(chatId, expected);

    }

    @Test
    public void shouldProperlyDeleteByGroupId() {
        Long chatId = 12334L;
        Integer groupId = 212;
        Update update = prepareUpdate(chatId, String.format("%s %s", DELETE_GROUP_SUB.getCommandName(), groupId));

        TelegramUser telegramUser = new TelegramUser();
        GroupSub groupSub = new GroupSub();
        groupSub.setId(groupId);
        groupSub.setTitle("g1");
        telegramUser.setChatId(chatId);
        telegramUser.setGroupSubs(Collections.singletonList(groupSub));
        ArrayList<TelegramUser> users = new ArrayList<>();
        users.add(telegramUser);
        groupSub.setUsers(users);
        Mockito.when(groupSubService.findById(groupId)).thenReturn(Optional.of(groupSub));
        Mockito.when(telegramUserService.findByChatId(chatId)).thenReturn(Optional.of(telegramUser));

        String expected = "Удалил подписку на группу: g1";

        command.execute(update);

        users.remove(telegramUser);
        Mockito.verify(groupSubService).save(groupSub);
        Mockito.verify(sendBotMessageService).sendMessage(chatId, expected);
    }

    @Test
    public void shouldDoesNotExistByGroupId() {
        Long chatId = 12314L;
        Integer groupId = 213213;
        Update update = prepareUpdate(chatId, String.format("%s %s", DELETE_GROUP_SUB.getCommandName(), groupId));

        Mockito.when(groupSubService.findById(groupId)).thenReturn(Optional.empty());

        String expected = "Не нашел такой группы =/";

        command.execute(update);

        Mockito.verify(sendBotMessageService).sendMessage(chatId, expected);
    }
}