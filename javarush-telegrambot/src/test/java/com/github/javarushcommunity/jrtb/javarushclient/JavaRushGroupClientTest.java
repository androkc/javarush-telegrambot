package com.github.javarushcommunity.jrtb.javarushclient;

import com.github.javarushcommunity.jrtb.javarushclient.dto.GroupDiscussionInfo;
import com.github.javarushcommunity.jrtb.javarushclient.dto.GroupInfo;
import com.github.javarushcommunity.jrtb.javarushclient.dto.GroupRequestArgs;
import com.github.javarushcommunity.jrtb.javarushclient.dto.GroupsCountRequestArgs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.javarushcommunity.jrtb.javarushclient.dto.GroupInfoType.TECH;

@DisplayName("Integration-level testing for JavaRushGroupClientImplTest")
public class JavaRushGroupClientTest {
    public static final String JAVARUSH_API_PATH = "https://javarush.ru/api/1.0/rest";
    private final JavaRushGroupClient groupClient = new JavaRushGroupClientImpl("https://javarush.com/api/1.0/rest");

    @Test
    public void shouldProperlyGetGroupsWithEmptyArgs() {
        GroupRequestArgs args = GroupRequestArgs.builder().build();

        List<GroupInfo> groupInfoList = groupClient.getGroupList(args);

        Assertions.assertNotNull(groupInfoList);
        Assertions.assertFalse(groupInfoList.isEmpty());
    }

    @Test
    public void shouldProperlyGetWithOffsetAndLimit() {
        GroupRequestArgs args = GroupRequestArgs.builder()
                .offset(1)
                .limit(3)
                .build();

        List<GroupInfo> groupInfoList = groupClient.getGroupList(args);

        Assertions.assertNotNull(groupInfoList);
        Assertions.assertEquals(3, groupInfoList.size());
    }

    @Test
    public void shouldProperlyGetGroupDisWithEmptyArgs() {
        GroupRequestArgs args = GroupRequestArgs.builder().build();

        List<GroupDiscussionInfo> groupDiscussionInfoList = groupClient.getGroupDiscussionList(args);

        Assertions.assertNotNull(groupDiscussionInfoList);
        Assertions.assertFalse(groupDiscussionInfoList.isEmpty());
    }

    @Test
    public void shouldProperlyGetGroupDiscWithOffSetAndLimit() {
        GroupRequestArgs args = GroupRequestArgs.builder()
                .offset(1)
                .limit(3)
                .build();

        List<GroupDiscussionInfo> groupDiscussionInfoList = groupClient.getGroupDiscussionList(args);

        Assertions.assertNotNull(groupDiscussionInfoList);
        Assertions.assertEquals(3, groupDiscussionInfoList.size());
    }

    @Test
    public void shouldProperlyGetGroupCount() {
        GroupsCountRequestArgs groupsCountRequestArgs = GroupsCountRequestArgs.builder().build();

        Integer groupCount = groupClient.getGroupCount(groupsCountRequestArgs);

        Assertions.assertEquals(26, groupCount);
    }

    @Test
    public void shouldProperlyGetGroupTECHCount() {
        GroupsCountRequestArgs args = GroupsCountRequestArgs.builder()
                .groupInfoType(TECH)
                .build();

        Integer groupCount = groupClient.getGroupCount(args);

        Assertions.assertEquals(7, groupCount);
    }

    @Test
    public void shouldProperlyGetGroupById() {
        Integer androidGroupId = 16;

        GroupDiscussionInfo groupById = groupClient.getGroupById(androidGroupId);

        Assertions.assertNotNull(groupById);
        Assertions.assertEquals(16, groupById.getId());
        Assertions.assertEquals(null, groupById.getGroupInfoType());
        Assertions.assertEquals("android", groupById.getKey());
    }
}

