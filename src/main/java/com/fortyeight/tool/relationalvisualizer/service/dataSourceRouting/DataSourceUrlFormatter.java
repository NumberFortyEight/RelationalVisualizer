package com.fortyeight.tool.relationalvisualizer.service.dataSourceRouting;

import com.fortyeight.tool.relationalvisualizer.dto.SimpleDataSourceInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataSourceUrlFormatter {
    public static final String DATA_SOURCE_URL_TEMPLATE = "jdbc:%s://%s:%d/%s";

    public String formatDataSourceUrl(SimpleDataSourceInfo info) {
        return DATA_SOURCE_URL_TEMPLATE.formatted(info.getServerName(),
                info.getHost(),
                info.getPortNumber(),
                info.getDatabaseName());
    }
}
