package com.github.javarushcommunity.jrtb.javarushclient.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

@Builder
@Getter
public class GroupsCountRequestArgs {
    private final String query;
    private final GroupInfoType groupInfoType;
    private final GroupFilter groupFilter;

    public Map<String, Object> populateQueries() {
        Map<String, Object> queries = new HashMap<>();
        if (nonNull(query)) {
            queries.put("query", query);
        }
        if (nonNull(groupInfoType)) {
            queries.put("type", groupInfoType);
        }
        if (nonNull(groupFilter)) {
            queries.put("filter", groupFilter);
        }
        return queries;
    }
}
