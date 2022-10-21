package com.fortyeight.tool.relationalvisualizer.config;

import com.fortyeight.tool.relationalvisualizer.service.DataSourceExtractor;
import com.fortyeight.tool.relationalvisualizer.service.dataSourceRouting.DataSourceUrlFormatter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class ExternalBootstrapDataSourceConfig {

    @Bean("defaultDataSourceMap")
    @ConditionalOnProperty(name = "bootstrap.datasource", havingValue = "external")
    public Map<Object, Object> getBootstrapDataSourceMap(@Qualifier("bootstrapDataSource") DataSource bootstrapDataSource) {
        return Map.of("defaultDataSourceName", bootstrapDataSource);
    }

    @Bean("bootstrapDataSource")
    @ConditionalOnProperty(name = "bootstrap.datasource", havingValue = "external")
    public DataSource getBootstrapDataSource(DataSourceExtractor extractor,
                                             DataSourceUrlFormatter dataSourceUrlFormatter,
                                             BootstrapDataSourceInfo info) {
        String url = dataSourceUrlFormatter.formatDataSourceUrl(info);
        return extractor.getDataSource(info, url);
    }
}