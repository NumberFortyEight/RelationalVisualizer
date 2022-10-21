package com.fortyeight.tool.relationalvisualizer.config;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;

@Component
public class DataSourceRouter extends AbstractRoutingDataSource {
    private final DataSourceContextHolder dataSourceContextHolder;

    public DataSourceRouter(DataSourceContextHolder dataSourceContextHolder) {
        this.dataSourceContextHolder = dataSourceContextHolder;
        super.setTargetDataSources(Map.of("defaultEmbeddedDataSource", defaultEmbeddedDataSource()));
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return dataSourceContextHolder.getCurrentDataSourceBeanName();
    }

    private DataSource defaultEmbeddedDataSource() {
        EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
        return dbBuilder.setType(EmbeddedDatabaseType.H2)
                .setName("defaultEmbeddedDataSource")
                .build();
    }
}