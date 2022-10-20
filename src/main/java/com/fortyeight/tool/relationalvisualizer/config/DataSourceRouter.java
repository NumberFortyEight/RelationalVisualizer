package com.fortyeight.tool.relationalvisualizer.config;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;

@Component
public class DataSourceRouter extends AbstractRoutingDataSource {
    private final DatabaseContextHolder databaseContextHolder;

    public DataSourceRouter(DatabaseContextHolder databaseContextHolder) {
        this.databaseContextHolder = databaseContextHolder;
        super.setTargetDataSources(Map.of("defaultEmbeddedDataSource", defaultEmbeddedDataSource()));
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return databaseContextHolder.getCurrentDataSourceBeanName();
    }

    private DataSource defaultEmbeddedDataSource() {
        EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
        return dbBuilder.setType(EmbeddedDatabaseType.H2)
                .setName("defaultEmbeddedDataSource")
                .build();
    }
}