package com.fortyeight.tool.relationalvisualizer.service;

import com.fortyeight.tool.relationalvisualizer.dto.Entry;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class EntryRowMapper implements RowMapper<Entry> {
    private final String table;
    private final String idColumnName;
    private final Map<String, String> referenceColumnNameToTable;

    private List<String> columnNames;

    public EntryRowMapper(String table, MetaDataService metaDataService) {
        this.table = table;
        this.idColumnName = metaDataService.getIdColumnName(table);
        this.referenceColumnNameToTable = metaDataService.getReferenceColumnToTable(table);
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
        for (String columnName : columnNames)
            values.add(resultSet.getString(columnName));

        return values;
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
            if (referenceColumnNameToTable.containsKey(column))
                refToId.put(referenceColumnNameToTable.get(column), value);
            if (idColumnName.equals(column))
                id = value;
        }

        Entry entry = new Entry();
        entry.setTable(table);
        entry.setId(id);
        if (!columnToValue.isEmpty())
            entry.setFields(columnToValue);

        if (!refToId.isEmpty())
            entry.setReferenceTableToId(refToId);

        return entry;
    }
}
