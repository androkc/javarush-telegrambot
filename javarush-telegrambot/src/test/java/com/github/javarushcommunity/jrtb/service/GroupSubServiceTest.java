package com.github.javarushcommunity.jrtb.service;

import com.github.javarushcommunity.jrtb.javarushclient.dto.GroupDiscussionInfo;
import com.github.javarushcommunity.jrtb.repository.GroupSubRepository;
import com.github.javarushcommunity.jrtb.repository.entity.GroupSub;
import com.github.javarushcommunity.jrtb.repository.entity.TelegramUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit-level testing for GroupSubService")
class GroupSubServiceTest {
    private GroupSubService groupSubService;
    private GroupSubRepository groupSubRepository;
    private TelegramUser telegramUser;
    private final static Long CHAT_ID = 1L;

    @BeforeEach
    public void init() {
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        groupSubRepository = Mockito.mock(GroupSubRepository.class);
        groupSubService = new GroupSubServiceImpl(groupSubRepository, telegramUserService);

        telegramUser = new TelegramUser();
        telegramUser.setChatId(CHAT_ID);
        telegramUser.setActive(true);

        Mockito.when(telegramUserService.findByChatId(CHAT_ID)).thenReturn(Optional.of(telegramUser));
    }

    @Test
    public void shouldProperlySaveGroup() {
        GroupDiscussionInfo groupDiscussionInfo = new GroupDiscussionInfo();
        groupDiscussionInfo.setId(1);
        groupDiscussionInfo.setTitle("g1");

        GroupSub expectedGroupSub = new GroupSub();
        expectedGroupSub.setId(groupDiscussionInfo.getId());
        expectedGroupSub.setTitle(groupDiscussionInfo.getTitle());
        expectedGroupSub.addUser(telegramUser);

        groupSubService.save(CHAT_ID, groupDiscussionInfo);

        Mockito.verify(groupSubRepository).save(expectedGroupSub);
    }

    @Test
    public void shouldProperlyAddUserToExistingGroup() {
        GroupDiscussionInfo groupDiscussionInfo = new GroupDiscussionInfo();
        groupDiscussionInfo.setId(1);
        groupDiscussionInfo.setTitle("g1");

        TelegramUser oldTelegramUser = new TelegramUser();
        oldTelegramUser.setChatId(2L);
        oldTelegramUser.setActive(true);

        GroupSub groupSubFromDB = new GroupSub();
        groupSubFromDB.setId(groupDiscussionInfo.getId());
        groupSubFromDB.setTitle(groupDiscussionInfo.getTitle());
        groupSubFromDB.addUser(oldTelegramUser);

        Mockito.when(groupSubRepository.findById(groupDiscussionInfo.getId())).thenReturn(Optional.of(groupSubFromDB));

        GroupSub expectedGroupSub = new GroupSub();
        expectedGroupSub.setId(groupDiscussionInfo.getId());
        expectedGroupSub.setTitle(groupDiscussionInfo.getTitle());
        expectedGroupSub.addUser(oldTelegramUser);
        expectedGroupSub.addUser(telegramUser);

        groupSubService.save(CHAT_ID, groupDiscussionInfo);

        Mockito.verify(groupSubRepository).findById(groupDiscussionInfo.getId());
        Mockito.verify(groupSubRepository).save(expectedGroupSub);
    }
}