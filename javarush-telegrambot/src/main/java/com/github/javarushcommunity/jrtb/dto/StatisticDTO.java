package com.github.javarushcommunity.jrtb.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class StatisticDTO {
    private final int activeUserCount;
    private final int inactiveUserCount;
    private final List<GroupStatDTO> groupStatDTOList;
    private final double averageGroupCountByUser;
}
