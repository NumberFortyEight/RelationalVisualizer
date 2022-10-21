package com.fortyeight.tool.relationalvisualizer.config;

import com.fortyeight.tool.relationalvisualizer.service.ResolvedDataSourceExtractorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;

@Component
public class DataSourceRouter extends AbstractRoutingDataSource {
    private final DataSourceContextHolder dataSourceContextHolder;
    private final ResolvedDataSourceExtractorService resolvedDataSourceExtractorService;

    public DataSourceRouter(DataSourceContextHolder dataSourceContextHolder,
                            ResolvedDataSourceExtractorService resolvedDataSourceExtractorService,
                            @Qualifier("defaultEmbeddedDataSourceMap")
                            Map<Object, Object> defaultDataSourceMap) {
        this.dataSourceContextHolder = dataSourceContextHolder;
        this.resolvedDataSourceExtractorService = resolvedDataSourceExtractorService;
        super.setTargetDataSources(defaultDataSourceMap);
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        Map<Object, DataSource> resolvedDataSources =
                resolvedDataSourceExtractorService.getResolvedDataSources(this);
        dataSourceContextHolder.setResolvedDataSources(resolvedDataSources);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return dataSourceContextHolder.getCurrentDataSourceBeanName();
    }
}