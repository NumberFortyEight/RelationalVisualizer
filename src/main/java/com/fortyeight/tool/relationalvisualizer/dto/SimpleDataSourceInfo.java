package com.fortyeight.tool.relationalvisualizer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimpleDataSourceInfo {
    private String dataSourceName;

    private String serverName;
    private String host;
    private String portNumber;
    private String databaseName;

    private String user;
    private String password;
}
