package com.fortyeight.tool.relationalvisualizer.config;

import com.fortyeight.tool.relationalvisualizer.advice.exception.DataSourceInfoException;
import com.fortyeight.tool.relationalvisualizer.dto.SimpleDataSourceInfo;
import com.fortyeight.tool.relationalvisualizer.service.DataSourceExtractor;
import com.fortyeight.tool.relationalvisualizer.service.MetaDataService;
import com.fortyeight.tool.relationalvisualizer.service.dataSourceRouting.DataSourceUrlFormatter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DataSourceContextHolder {
    private final ApplicationContext context;
    private final DataSourceExtractor dataSourceExtractor;
    private final DataSourceUrlFormatter dataSourceUrlFormatter;
    public static final String DEFAULT_DATA_SOURCE_NAME = "defaultDataSourceName";

    private String currentDataSourceName;
    @Setter
    private Map<Object, DataSource> resolvedDataSources;

    @PostConstruct
    private void init() {
        switchTo(DEFAULT_DATA_SOURCE_NAME);
    }

    public void set(SimpleDataSourceInfo info) {
        String url = dataSourceUrlFormatter.formatDataSourceUrl(info);
        DataSource dataSource = dataSourceExtractor.getDataSource(info, url);

        String dataSourceName = info.getDataSourceName();
        validateDataSourceName(dataSourceName, resolvedDataSources);
        resolvedDataSources.put(dataSourceName, dataSource);
        currentDataSourceName = dataSourceName;
        checkDataSource(info, url, resolvedDataSources);
    }

    private void checkDataSource(SimpleDataSourceInfo info,
                                 String url,
                                 Map<Object, DataSource> resolvedDataSources) {
        try {
            context.getBean(MetaDataService.class).getTableNames();
        } catch (Exception e) {
            resolvedDataSources.remove(info.getDataSourceName());
            switchTo(DEFAULT_DATA_SOURCE_NAME);
            throw new DataSourceInfoException("Cannot connect to: \"%s\" with info - %s. Message - %s".formatted(url, info, e.getMessage()));
        }
    }

    private void validateDataSourceName(String dataSourceName, Map<Object, DataSource> resolvedDataSources) {
        if (resolvedDataSources.get(dataSourceName) != null)
            throw new DataSourceInfoException("DataSourceName \"%s\" always exist".formatted(dataSourceName));
    }

    public Object getCurrentDataSourceBeanName() {
        return currentDataSourceName;
    }

    public List<String> getDataSourceNames() {
        return resolvedDataSources.keySet()
                .stream()
                .map(o -> ((String) o))
                .toList();
    }

    public void switchTo(String dataSourceName) {
        if (resolvedDataSources != null)
            if (resolvedDataSources.get(dataSourceName) == null)
                throw new DataSourceInfoException("DataSource with name - %s not found".formatted(dataSourceName));
        currentDataSourceName = dataSourceName;
    }
}
