package com.github.javarushcommunity.jrtb.javarushclient;

import com.github.javarushcommunity.jrtb.javarushclient.dto.PostInfo;
import com.github.javarushcommunity.jrtb.repository.GroupSubRepository;
import com.github.javarushcommunity.jrtb.repository.entity.GroupSub;
import kong.unirest.GenericType;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Component

public class JavaRushPostClientImpl implements JavaRushPostClient {
    private final String javarushApiPostPath;
    @Autowired
    private final GroupSubRepository groupSubRepository;

    public JavaRushPostClientImpl(@Value("${javarush.api.path}") String javarushApi, GroupSubRepository groupSubRepository) {
        this.javarushApiPostPath = javarushApi + "/posts";
        this.groupSubRepository = groupSubRepository;
    }

    @Override
    public List<PostInfo> findNewPosts(Integer groupId, Integer lastPostId) {
        List<PostInfo> lastPostInfoList = Unirest.get(javarushApiPostPath)
                .queryString("order", "NEW")
                .queryString("groupKid", groupId)
                .queryString("limit", 15)
                .asObject(new GenericType<List<PostInfo>>() {
                }).getBody();
        List<PostInfo> newPosts = new ArrayList<>();
        for (PostInfo e : lastPostInfoList) {

            if (isNull(lastPostId)) {
                Optional<GroupSub> byId = groupSubRepository.findById(groupId);
                if (byId.isPresent()) {
                    byId.get().setLastArticleId(e.getId());
                    newPosts.add(e);
                }
                return newPosts;
            }

            if (lastPostId.equals(e.getId())) {
                return newPosts;
            }
            newPosts.add(e);
        }
        return newPosts;
    }
}
