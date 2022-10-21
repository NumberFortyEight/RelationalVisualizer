package com.fortyeight.tool.relationalvisualizer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class DefaultEmbeddedDataSourceConfig {

    @Bean("defaultEmbeddedDataSourceMap")
    public Map<Object, Object> getDefaultDataSourceMap() {
        return Map.of("defaultEmbeddedDataSource", getDefaultEmbeddedDataSource());
    }

    private DataSource getDefaultEmbeddedDataSource() {
        EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
        return dbBuilder.setType(EmbeddedDatabaseType.H2)
                .setName("defaultEmbeddedDataSource")
                .build();
    }
}