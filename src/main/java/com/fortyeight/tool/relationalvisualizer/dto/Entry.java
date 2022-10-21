package com.fortyeight.tool.relationalvisualizer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Entry {
    private String table;

    private String id;
    private Map<String, String> fields;
    private Map<String, String> referenceTableToId;
}
