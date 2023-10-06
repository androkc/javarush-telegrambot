package com.github.javarushcommunity.jrtb.javarushclient.dto;

import lombok.Data;

@Data
public class UserDiscussionInfo {
    private boolean isBookmarked;
    private Integer lastTime;
    private Integer newCommentsCount;
}
