package com.fortyeight.tool.relationalvisualizer.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MetaDataService {
    private final JdbcTemplate jdbcTemplate;


    @SneakyThrows
    public List<String> getTableNames() {
        Connection connection = jdbcTemplate.getDataSource().getConnection();
        ResultSet tables = connection
                .getMetaData()
                .getTables(null, null, "%", new String[] {"TABLE"});
        ArrayList<String> tableNames = new ArrayList<>();
        while (tables.next())
            tableNames.add(tables.getString("TABLE_NAME"));
        connection.close();
        return tableNames;
    }
}
