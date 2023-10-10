package com.github.javarushcommunity.jrtb.javarushclient;

import com.github.javarushcommunity.jrtb.javarushclient.dto.PostInfo;
import com.github.javarushcommunity.jrtb.repository.GroupSubRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static com.github.javarushcommunity.jrtb.javarushclient.JavaRushGroupClientTest.JAVARUSH_API_PATH;

@DisplayName("Integration-level testing for JavaRushPostClient")
class JavaRushPostClientTest {
    private final GroupSubRepository groupSubRepository = Mockito.mock(GroupSubRepository.class);
    private final JavaRushPostClient javaRushPostClient = new JavaRushPostClientImpl(JAVARUSH_API_PATH, groupSubRepository);

    @Test
    public void shouldProperlyGetNew15Posts() {
        List<PostInfo> newPosts = javaRushPostClient.findNewPosts(30, 2900);

        Assertions.assertEquals(15, newPosts.size());
    }
}