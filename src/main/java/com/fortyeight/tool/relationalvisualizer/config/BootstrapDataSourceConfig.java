package com.fortyeight.tool.relationalvisualizer.config;

import com.fortyeight.tool.relationalvisualizer.service.DataSourceExtractor;
import com.fortyeight.tool.relationalvisualizer.service.dataSourceRouting.DataSourceUrlFormatter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class BootstrapDataSourceConfig {

    @Bean("bootstrapDataSourceMap")
    public Map<Object, Object> getBootstrapDataSourceMap(@Qualifier("bootstrapDataSource") DataSource bootstrapDataSource) {
        return Map.of("bootstrapDataSource", bootstrapDataSource);
    }

    @Bean("bootstrapDataSource")
    public DataSource getBootstrapDataSource(DataSourceExtractor extractor,
                                             DataSourceUrlFormatter dataSourceUrlFormatter,
                                             BootstrapDataSourceInfo info) {
        String url = dataSourceUrlFormatter.formatDataSourceUrl(info);
        return extractor.getDataSource(info, url);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("bootstrapDataSource") DataSource bootstrapDataSource) {
        return new JdbcTemplate(bootstrapDataSource);
    }
}