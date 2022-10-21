package com.fortyeight.tool.relationalvisualizer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class SimpleDataSourceInfo {

    @NotEmpty
    @Size(min = 7, message = "dataSourceName should have at least 2 characters")
    private String dataSourceName;

    @NotEmpty
    private String serverName;

    @NotEmpty
    private String host;

    @NotEmpty
    private int portNumber;

    @NotEmpty
    private String databaseName;

    @NotEmpty
    private String user;

    @NotEmpty
    private String password;
}
