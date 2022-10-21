package com.fortyeight.tool.relationalvisualizer.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class Entry {
    private String table;

    private String id;
    private Map<String, String> fields;
    private Map<String, String> referenceTableToId;
}
