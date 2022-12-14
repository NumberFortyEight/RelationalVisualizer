package com.fortyeight.tool.relationalvisualizer.service;

import com.fortyeight.tool.relationalvisualizer.dto.Entry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EntryPointService {
    private final JdbcTemplate template;
    private final MetaDataService metaDataService;

    public List<Entry> getByTable(String table) {
        return template.query("select * from " + table, new EntryRowMapper(table, metaDataService));
    }

    public List<Entry> getByTableAndId(String table, String id) {
        String idColumnName = metaDataService.getIdColumnName(table);
        return template.query("select * from %s where %s = '%s'".formatted(table, idColumnName, id), new EntryRowMapper(table, metaDataService));
    }
}
