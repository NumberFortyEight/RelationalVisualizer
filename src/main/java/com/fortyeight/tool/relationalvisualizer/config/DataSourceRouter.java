package com.fortyeight.tool.relationalvisualizer.config;

import com.fortyeight.tool.relationalvisualizer.service.ResolvedDataSourceExtractorService;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.Map;

@Component
public class DataSourceRouter extends AbstractRoutingDataSource {
    private final DataSourceContextHolder dataSourceContextHolder;
    private final ResolvedDataSourceExtractorService resolvedDataSourceExtractorService;

    public DataSourceRouter(DataSourceContextHolder dataSourceContextHolder,
                            ResolvedDataSourceExtractorService resolvedDataSourceExtractorService) {
        this.dataSourceContextHolder = dataSourceContextHolder;
        this.resolvedDataSourceExtractorService = resolvedDataSourceExtractorService;
        super.setTargetDataSources(Map.of("defaultEmbeddedDataSource", defaultEmbeddedDataSource()));
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

    private DataSource defaultEmbeddedDataSource() {
        EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
        return dbBuilder.setType(EmbeddedDatabaseType.H2)
                .setName("defaultEmbeddedDataSource")
                .build();
    }
}