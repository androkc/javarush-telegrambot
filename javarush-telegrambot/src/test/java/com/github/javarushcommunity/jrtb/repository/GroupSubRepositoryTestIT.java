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
class GroupSubRepositoryTestIT {
    @Autowired
    private GroupSubRepository groupSubRepository;

    @Sql(scripts = {"/sql/clearDbs.sql","/sql/fiveUsersForGroupSub.sql"})
    @Test
    public void shouldProperlyGetAllUsersForGroupSub() {
        Optional<GroupSub> subRepositoryById = groupSubRepository.findById(1);

        Assertions.assertTrue(subRepositoryById.isPresent());
        Assertions.assertEquals(1, subRepositoryById.get().getId());
        List<TelegramUser> users = subRepositoryById.get().getUsers();
        for (int i = 0; i < users.size(); i++) {
            Assertions.assertEquals(Long.valueOf(i + 1), users.get(i).getChatId());
            Assertions.assertTrue(users.get(i).isActive());
        }
    }
}