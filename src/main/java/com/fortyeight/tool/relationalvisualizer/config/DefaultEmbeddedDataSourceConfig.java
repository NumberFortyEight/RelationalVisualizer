package com.fortyeight.tool.relationalvisualizer.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.Map;

//@Configuration
public class DefaultEmbeddedDataSourceConfig {

    @Bean("defaultEmbeddedDataSourceMap")
    public Map<Object, Object> getDefaultDataSourceMap(@Qualifier("defaultEmbeddedDataSource") DataSource defaultEmbeddedDataSource) {
        return Map.of("defaultEmbeddedDataSource", defaultEmbeddedDataSource);
    }

    @Bean("defaultEmbeddedDataSource")
    public DataSource getDefaultEmbeddedDataSource() {
        EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
        return dbBuilder.setType(EmbeddedDatabaseType.H2)
                .setName("defaultEmbeddedDataSource")
                .build();
    }
}