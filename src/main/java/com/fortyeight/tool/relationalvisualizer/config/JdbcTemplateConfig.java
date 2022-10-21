package com.fortyeight.tool.relationalvisualizer.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class JdbcTemplateConfig {
    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("bootstrapDataSource") DataSource bootstrapDataSource) {
        return new JdbcTemplate(bootstrapDataSource);
    }
}
