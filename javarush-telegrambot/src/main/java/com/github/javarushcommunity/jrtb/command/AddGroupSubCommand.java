package com.github.javarushcommunity.jrtb.command;

import com.github.javarushcommunity.jrtb.javarushclient.JavaRushGroupClient;
import com.github.javarushcommunity.jrtb.javarushclient.dto.GroupDiscussionInfo;
import com.github.javarushcommunity.jrtb.javarushclient.dto.GroupRequestArgs;
import com.github.javarushcommunity.jrtb.repository.entity.GroupSub;
import com.github.javarushcommunity.jrtb.service.GroupSubService;
import com.github.javarushcommunity.jrtb.service.SendBotMessageService;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

import static com.github.javarushcommunity.jrtb.command.CommandNameEnum.ADD_GROUP_SUB;
import static com.github.javarushcommunity.jrtb.command.CommandUtils.getChatId;
import static com.github.javarushcommunity.jrtb.command.CommandUtils.getMessage;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNumeric;

@RequiredArgsConstructor
public class AddGroupSubCommand implements Command {
    private final GroupSubService groupSubService;
    private final SendBotMessageService sendBotMessageService;
    private final JavaRushGroupClient javaRushGroupClient;

    @Override
    public void execute(Update update) {
        if (getMessage(update).equalsIgnoreCase(ADD_GROUP_SUB.getCommandName())) {
            sendGroupIdList(getChatId(update));
            return;
        }
        String groupId = getMessage(update).split(SPACE)[1];
        Long chatId = getChatId(update);
        if (isNumeric(groupId)) {
            GroupDiscussionInfo groupById = javaRushGroupClient.getGroupById(Integer.parseInt(groupId));
            if (isNull(groupById.getId())) {
                sendGroupNotFound(chatId, groupId);
            }
            GroupSub save = groupSubService.save(chatId, groupById);
            sendBotMessageService.sendMessage(chatId, "Подписал на группу " + save.getTitle());
        } else {
            sendGroupNotFound(chatId, groupId);
        }
    }

    public void sendGroupNotFound(Long chatId, String groupId) {
        String groupNotFoundMessage = "Нет группы с ID=\"%s\"";
        sendBotMessageService.sendMessage(chatId, String.format(groupNotFoundMessage, groupId));
    }

    public void sendGroupIdList(Long chatId) {
        String groupIds = javaRushGroupClient.getGroupList(GroupRequestArgs.builder().build()).stream()
                .map(groupInfo -> String.format("%s - %s \n", groupInfo.getTitle(), groupInfo.getId()))
                .collect(Collectors.joining());

        String message = "Чтобы подписаться на группу - передай команду вместе с ID группы. \n" +
                "Например: /add_group_sub 16. \n\n" +
                "я подготовил список всех групп - выбирай какую хочешь :) \n\n" +
                "имя группы - ID группы \n\n" +
                "%s";

        sendBotMessageService.sendMessage(chatId, String.format(message, groupIds));
    }
}
