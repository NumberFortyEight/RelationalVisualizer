package com.fortyeight.tool.relationalvisualizer.service;

import com.fortyeight.tool.relationalvisualizer.advice.exception.EntryPresentationException;
import com.fortyeight.tool.relationalvisualizer.dto.Entry;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Slf4j
public class EntryRowMapper implements RowMapper<Entry> {
    private final JdbcTemplate template;
    private final String table;
    private final String idColumnName;
    private final List<String> referenceColumnNames;

    private List<String> columnNames;

    public EntryRowMapper(String table, JdbcTemplate template) {
        this.table = table;
        this.template = template;
        this.referenceColumnNames = getReferenceColumnNames();
        this.idColumnName = getIdColumnName();
    }

    @Override
    public Entry mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        if (columnNames == null)
            columnNames = getColumnNames(resultSet);
        List<String> values = getValues(resultSet);
        Entry entry = constructEntry(values);
        log.debug("Entry created - {}", entry);
        return entry;
    }

    private List<String> getValues(ResultSet resultSet) throws SQLException {
        List<String> values = new ArrayList<>();
        for (String columnName : columnNames) {
            values.add(resultSet.getString(columnName));
        }
        return values;
    }

    @SneakyThrows
    private String getIdColumnName() {
        try (Connection connection = Objects.requireNonNull(template.getDataSource()).getConnection()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getExportedKeys(null, null, table);
            resultSet.next();
            return resultSet.getString("PKCOLUMN_NAME");
        } catch (Exception e) {
            throw new EntryPresentationException("cannot get id column");
        }
    }

    @SneakyThrows
    private List<String> getReferenceColumnNames() {
        List<String> referenceColumns = new ArrayList<>();
        try (Connection connection = Objects.requireNonNull(template.getDataSource()).getConnection()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getImportedKeys(null, null, table);
            while (resultSet.next()) {
                referenceColumns.add(resultSet.getString(8));
            }
        } catch (Exception e) {
            throw new EntryPresentationException("cannot get reference column");
        }
        return referenceColumns;
    }

    @SneakyThrows
    private List<String> getColumnNames(ResultSet resultSet) {
        ResultSetMetaData metaData = resultSet.getMetaData();
        List<String> columnNames = new ArrayList<>();
        for (int i = 1; i < metaData.getColumnCount() + 1; i++) {
            columnNames.add(metaData.getColumnName(i));
        }
        return columnNames;
    }

    private Entry constructEntry(List<String> values) {
        String id = null;
        HashMap<String, String> refToId = new HashMap<>();
        HashMap<String, String> columnToValue = new HashMap<>();
        for (int i = 0; i < columnNames.size(); i++) {
            String column = columnNames.get(i);
            String value = values.get(i);
            if (value == null) continue;
            columnToValue.put(column, value);
            if (referenceColumnNames.contains(column))
                refToId.put(column, value);
            if (idColumnName.equals(column))
                id = value;
        }

        return Entry.builder()
                .table(table)
                .id(id)
                .fields(columnToValue)
                .referenceTableToId(refToId)
                .build();
    }
}
