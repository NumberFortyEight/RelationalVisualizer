package com.fortyeight.tool.relationalvisualizer.config;

import com.fortyeight.tool.relationalvisualizer.advice.exception.DataSourceInfoException;
import com.fortyeight.tool.relationalvisualizer.dto.SimpleDataSourceInfo;
import com.fortyeight.tool.relationalvisualizer.service.DataSourceUrlFormatter;
import com.fortyeight.tool.relationalvisualizer.service.MetaDataService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DataSourceContextHolder {
    private final DataSourceUrlFormatter dataSourceUrlFormatter;
    public static final String DEFAULT_DATA_SOURCE_NAME = "defaultEmbeddedDataSource";

    private String currentDataSourceName;
    @Setter
    private Map<Object, DataSource> resolvedDataSources;

    @PostConstruct
    private void init() {
        switchToDefault();
    }

    public void set(SimpleDataSourceInfo info) {
        String url = dataSourceUrlFormatter.formatDataSourceUrl(info);
        DataSource dataSource = DataSourceBuilder.create()
                .username(info.getUser())
                .password(info.getPassword())
                .url(url)
                .build();
        String dataSourceName = info.getDataSourceName();
        validateDataSourceName(dataSourceName, resolvedDataSources);
        resolvedDataSources.put(dataSourceName, dataSource);
        currentDataSourceName = dataSourceName;
        //checkDataSource(info, url, resolvedDataSources);
    }

    /*private void checkDataSource(SimpleDataSourceInfo info,
                                 String url,
                                 Map<Object, DataSource> resolvedDataSources) {
        try {
            //metaDataService.getTableNames();
        } catch(Exception e) {
            resolvedDataSources.remove(info.getDataSourceName());
            throw new DataSourceInfoException("Cannot connect to: \"%s\" with info - %s".formatted(url, info));
        }
    }*/

    private void validateDataSourceName(String dataSourceName, Map<Object, DataSource> resolvedDataSources) {
        if (resolvedDataSources.get(dataSourceName) != null)
            throw new DataSourceInfoException("DataSourceName \"%s\" always exist".formatted(dataSourceName));
    }

    public Object getCurrentDataSourceBeanName() {
        return currentDataSourceName;
    }

    public void switchToDefault() {
        currentDataSourceName = DEFAULT_DATA_SOURCE_NAME;
    }
}
