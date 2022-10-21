package com.fortyeight.tool.relationalvisualizer.service;

import com.fortyeight.tool.relationalvisualizer.config.DataSourceRouter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResolvedDataSourceExtractorService {
    @NonNull
    @SuppressWarnings("unchecked")
    public Map<Object, DataSource> getResolvedDataSources(DataSourceRouter dataSourceRouter) {
        try {
            Field resolvedDataSources = DataSourceRouter.class
                    .getSuperclass()
                    .getDeclaredField("resolvedDataSources");
            resolvedDataSources.setAccessible(true);
            return (Map<Object, DataSource>) resolvedDataSources.get(dataSourceRouter);
        } catch (Exception e) {
            String message = "unable to extract resolvedDataSources";
            log.error(message);
            throw new IllegalStateException(message);
        }
    }
}
