package com.fortyeight.tool.relationalvisualizer.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Entry {
    private String table;

    private String id;
    private Map<String, String> fields;
    private Map<String, String> referenceTableToId;
}
