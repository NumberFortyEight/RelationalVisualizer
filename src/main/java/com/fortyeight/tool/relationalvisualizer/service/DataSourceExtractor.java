package com.fortyeight.tool.relationalvisualizer.service;

import com.fortyeight.tool.relationalvisualizer.dto.SimpleDataSourceInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataSourceExtractor {
    public DataSource getDataSource(SimpleDataSourceInfo info, String url) {
        return DataSourceBuilder.create()
                .username(info.getUser())
                .password(info.getPassword())
                .url(url)
                .build();
    }
}
