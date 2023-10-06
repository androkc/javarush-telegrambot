package com.github.javarushcommunity.jrtb.javarushclient.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@Data
@ToString
public class GroupDiscussionInfo extends GroupInfo {
    private UserDiscussionInfo userDiscussionInfo;
    private Integer commentsCount;
}
