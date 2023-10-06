package com.github.javarushcommunity.jrtb.javarushclient.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GroupInfo {
    private Integer id;
    private String avatarUrl;
    private String createTime;
    private String description;
    private String key;
    private Integer levelEditor;
    private MeGroupInfo meGroupInfo;
    private String pictureUrl;
    private String title;
    private GroupInfoType groupInfoType;
    private Integer usersCount;
    private GroupVisibilityStatus groupVisibilityStatus;
}
