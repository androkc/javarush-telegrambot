package com.github.javarushcommunity.jrtb.repository;

import com.github.javarushcommunity.jrtb.repository.entity.GroupSub;
import com.github.javarushcommunity.jrtb.repository.entity.TelegramUser;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class TelegramUserRepositoryIT {
    @Autowired
    private TelegramUserRepository telegramUserRepository;

    @Sql(scripts = {"/sql/clearDbs.sql", "/sql/telegram_users.sql"})
    @Test
    public void shouldProperlyFindAllActiveUsers() {
        List<TelegramUser> usersActive = telegramUserRepository.findAllByActiveTrue();

        Assertions.assertEquals(5, usersActive.size());
    }

    @Sql(scripts = {"/sql/clearDbs.sql"})
    @Test
    public void shouldProperlySaveTelegramUser() {
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setActive(false);
        telegramUser.setChatId("12313123");
        telegramUserRepository.save(telegramUser);

        Optional<TelegramUser> saved = telegramUserRepository.findById(telegramUser.getChatId());

        Assertions.assertTrue(saved.isPresent());
        Assertions.assertEquals(telegramUser, saved.get());
    }

    @Sql(scripts = {"/sql/fiveGroupSubsForUser.sql"})
    @Test
    public void shouldProperlyGetAllGroupsSubsForUser() {
        Optional<TelegramUser> userRepositoryById = telegramUserRepository.findById("1");

        Assertions.assertTrue(userRepositoryById.isPresent());

        List<GroupSub> groupSubs = userRepositoryById.get().getGroupSubs();
        for (int i = 0; i < groupSubs.size(); i++) {
            Assertions.assertEquals(String.format("g%s", i + 1), groupSubs.get(i).getTitle());
            Assertions.assertEquals(i + 1, groupSubs.get(i).getId());
            Assertions.assertEquals(i + 1, groupSubs.get(i).getLastArticleId());
        }
    }
}
