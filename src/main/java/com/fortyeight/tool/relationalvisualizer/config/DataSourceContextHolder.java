package com.fortyeight.tool.relationalvisualizer.config;

import com.fortyeight.tool.relationalvisualizer.dto.SimpleDataSourceInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Service
@RequiredArgsConstructor
public class DataSourceContextHolder {

    private static final String DATA_SOURCE_URL_TEMPLATE = "jdbc:%s://%s:%s/%s";
    private final DefaultListableBeanFactory beanFactory;
    private final ThreadLocal<String> currentDataSourceBeanNameContext = new ThreadLocal<>();

    @PostConstruct
    private void init() {
        currentDataSourceBeanNameContext.set("defaultEmbeddedDataSource");
    }

    public void set(SimpleDataSourceInfo info) {
        // TODO: 21.10.2022 validation required
        DataSource dataSource = DataSourceBuilder.create()
                .username(info.getUser())
                .password(info.getPassword())
                .url(formatDataSourceUrl(info))
                .build();
        String dataSourceName = info.getDataSourceName();
        beanFactory.registerSingleton(dataSourceName, dataSource);
        currentDataSourceBeanNameContext.set(dataSourceName);
    }

    private String formatDataSourceUrl(SimpleDataSourceInfo info) {
        return DATA_SOURCE_URL_TEMPLATE.formatted(info.getServerName(),
                info.getHost(),
                info.getPortNumber(),
                info.getDatabaseName());
    }

    public Object getCurrentDataSourceBeanName() {
        return currentDataSourceBeanNameContext.get();
    }
}
