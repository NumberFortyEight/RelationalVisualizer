package com.fortyeight.tool.relationalvisualizer.service.dataSourceRouting;

import com.fortyeight.tool.relationalvisualizer.config.DataSourceContextHolder;
import com.fortyeight.tool.relationalvisualizer.dto.SimpleDataSourceInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataSourceContextHolderService {
    private final DataSourceContextHolder dataSourceContextHolder;
    private final SimpleDataSourceInfoValidator simpleDataSourceInfoValidator;

    public void set(SimpleDataSourceInfo simpleDataSourceInfo) {
        simpleDataSourceInfoValidator.validate(simpleDataSourceInfo);
        dataSourceContextHolder.set(simpleDataSourceInfo);
    }

    public List<String> getDataSourceNames() {
        return dataSourceContextHolder.getDataSourceNames();
    }

    public void switchTo(String dataSourceName) {
        dataSourceContextHolder.switchTo(dataSourceName);
    }
}