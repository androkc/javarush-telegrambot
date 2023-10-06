package com.github.javarushcommunity.jrtb.javarushclient.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

@Getter
@Builder
public class GroupRequestArgs {
    private final String query;
    private final GroupInfoType type;
    private final GroupFilter groupFilter;

    private final Integer offset;
    private final Integer limit;

    public Map populateQueries() {
        Map queries = new HashMap<>();
        if (nonNull(query)) {
            queries.put("query", query);
        }
        if (nonNull(type)) {
            queries.put("type", type);
        }
        if (nonNull(groupFilter)) {
            queries.put("filter", groupFilter);
        }
        if (nonNull(offset)) {
            queries.put("offset", offset);
        }
        if (nonNull(limit)) {
            queries.put("limit", limit);
        }
        return queries;
    }
}
