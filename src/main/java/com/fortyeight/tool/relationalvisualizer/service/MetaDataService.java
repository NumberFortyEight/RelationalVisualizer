package com.fortyeight.tool.relationalvisualizer.service;

import com.fortyeight.tool.relationalvisualizer.advice.exception.EntryPresentationException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MetaDataService {
    private final JdbcTemplate template;


    @SneakyThrows
    public List<String> getTableNames() {
        ArrayList<String> tableNames = new ArrayList<>();
        try (Connection connection = Objects.requireNonNull(template.getDataSource()).getConnection()) {
            ResultSet tables = connection
                    .getMetaData()
                    .getTables(null, null, "%", new String[] {"TABLE"});
            while (tables.next())
                tableNames.add(tables.getString("TABLE_NAME"));
        } catch (Exception e) {
            throw new EntryPresentationException("cannot get references map");
        }
        return tableNames;
    }

    @SneakyThrows
    public Map<String, String> getReferenceColumnToTable(String table) {
        Map<String, String> map = new HashMap<>();
        try (Connection connection = Objects.requireNonNull(template.getDataSource()).getConnection()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getImportedKeys(null, null, table);
            while (resultSet.next()) {
                String referenceTable = resultSet.getString(3);
                String referenceColumn = resultSet.getString(8);
                map.put(referenceColumn, referenceTable);
            }
        } catch (Exception e) {
            throw new EntryPresentationException("cannot get references map");
        }
        return map;
    }

    @SneakyThrows
    public String getIdColumnName(String table) {
        try (Connection connection = Objects.requireNonNull(template.getDataSource()).getConnection()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getExportedKeys(null, null, table);
            resultSet.next();
            return resultSet.getString("PKCOLUMN_NAME");
        } catch (Exception e) {
            throw new EntryPresentationException("cannot get id column");
        }
    }
}
