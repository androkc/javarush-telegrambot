package com.github.javarushcommunity.jrtb.command;

import com.github.javarushcommunity.jrtb.repository.entity.GroupSub;
import com.github.javarushcommunity.jrtb.repository.entity.TelegramUser;
import com.github.javarushcommunity.jrtb.service.GroupSubService;
import com.github.javarushcommunity.jrtb.service.SendBotMessageService;
import com.github.javarushcommunity.jrtb.service.TelegramUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.javarushcommunity.jrtb.command.CommandNameEnum.DELETE_GROUP_SUB;
import static com.github.javarushcommunity.jrtb.command.CommandUtils.getChatId;
import static com.github.javarushcommunity.jrtb.command.CommandUtils.getMessage;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNumeric;


@RequiredArgsConstructor
public class DeleteGroupSubCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final GroupSubService groupSubService;

    @Override
    public void execute(Update update) {
        if (getMessage(update).equalsIgnoreCase(DELETE_GROUP_SUB.getCommandName())) {
            sendGroupIdList(getChatId(update).toString());
            return;
        }
        String groupId = getMessage(update).split(SPACE)[1];
        Long chatId = getChatId(update);
        if (isNumeric(groupId)) {
            Optional<GroupSub> byId = groupSubService.findById(Integer.valueOf(groupId));
            if (byId.isPresent()) {
                GroupSub groupSub = byId.get();
                TelegramUser telegramUser = telegramUserService.findByChatId(String.valueOf(chatId)).orElseThrow(NotFoundException::new);
                groupSub.getUsers().remove(telegramUser);
                groupSubService.save(groupSub);
                sendBotMessageService.sendMessage(String.valueOf(chatId), format("Удалил подписку на группу: %s", groupSub.getTitle()));
//                telegramUser.getGroupSubs().remove(groupSub);
            } else {
                sendBotMessageService.sendMessage(String.valueOf(chatId), "Не нашел такой группы =/");
            }
        } else {
            sendBotMessageService.sendMessage(String.valueOf(chatId), "неправильный формат ID группы.\n " +
                    "ID должно быть целым положительным числом");
        }
    }

    private void sendGroupIdList(String chatId) {
        String message;
        List<GroupSub> groupSubList = telegramUserService.findByChatId(chatId)
                .orElseThrow(NotFoundException::new)
                .getGroupSubs();
        if (CollectionUtils.isEmpty(groupSubList)) {
            message = "Пока нет подписок на группы. Чтобы добавить подписку напиши /add_group_sub";
        } else {
            String userGroupSubData = groupSubList.stream()
                    .map(group -> format("%s - %s \n", group.getTitle(), group.getId()))
                    .collect(Collectors.joining());

            message = String.format("Чтобы удалить подписку на группу - передай комадну вместе с ID группы. \n" +
                    "Например: /delete_group_sub 16 \n\n" +
                    "я подготовил список всех групп, на которые ты подписан) \n\n" +
                    "имя группы - ID группы \n\n" +
                    "%s", userGroupSubData);
        }


        sendBotMessageService.sendMessage(chatId, format(message));
    }

}
