package com.fortyeight.tool.relationalvisualizer.config;

import com.fortyeight.tool.relationalvisualizer.service.dataSourceRouting.ResolvedDataSourcesExtractorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;

@Component
public class DataSourceRouter extends AbstractRoutingDataSource {
    private final DataSourceContextHolder dataSourceContextHolder;
    private final ResolvedDataSourcesExtractorService resolvedDataSourcesExtractorService;

    public DataSourceRouter(DataSourceContextHolder dataSourceContextHolder,
                            ResolvedDataSourcesExtractorService resolvedDataSourcesExtractorService,
                            @Qualifier("bootstrapDataSourceMap")
                            Map<Object, Object> defaultDataSourceMap) {
        this.dataSourceContextHolder = dataSourceContextHolder;
        this.resolvedDataSourcesExtractorService = resolvedDataSourcesExtractorService;
        super.setTargetDataSources(defaultDataSourceMap);
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        Map<Object, DataSource> resolvedDataSources =
                resolvedDataSourcesExtractorService.getResolvedDataSources(this);
        dataSourceContextHolder.setResolvedDataSources(resolvedDataSources);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return dataSourceContextHolder.getCurrentDataSourceBeanName();
    }
}