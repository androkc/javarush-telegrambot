package com.github.javarushcommunity.jrtb.javarushclient.dto;

import lombok.Data;

import java.util.Map;

@Data
public class PostInfo {
    private String alternativeGroupKey;
    private Map<String, String> alternatives;
    private AuthorInfo authorInfo;
    private Integer commentsCount;
    private String content;
    private Long createdTime;
    private String description;
    private GroupInfo groupInfo;
    private Integer id;
    private String key;
    private Language language;
    private LikesInfo likesInfo;
    private GroupInfo originalGroupInfo;
    private String pictureUrl;
    private Double rating;
    private Integer ratingCount;
    private String title;
    private String type;
    private Long updatedTime;
    private UserDiscussionInfo userDiscussionInfo;
    private Integer views;
    private VisibilityStatus visibilityStatus;
}
