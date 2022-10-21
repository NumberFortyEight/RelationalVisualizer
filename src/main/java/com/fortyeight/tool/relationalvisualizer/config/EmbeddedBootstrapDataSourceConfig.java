package com.fortyeight.tool.relationalvisualizer.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class EmbeddedBootstrapDataSourceConfig {

    @Bean("defaultDataSourceMap")
    @ConditionalOnProperty(name = "bootstrap.datasource", havingValue = "embedded")
    public Map<Object, Object> getDefaultDataSourceMap(@Qualifier("bootstrapDataSource") DataSource dataSource) {
        return Map.of("defaultDataSourceName", getDefaultEmbeddedDataSource());
    }

    @Bean("bootstrapDataSource")
    @ConditionalOnProperty(name = "bootstrap.datasource", havingValue = "embedded")
    public DataSource getDefaultEmbeddedDataSource() {
        EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
        return dbBuilder.setType(EmbeddedDatabaseType.H2)
                .setName("defaultDataSourceName")
                .build();
    }
}