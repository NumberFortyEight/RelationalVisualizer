package com.fortyeight.tool.relationalvisualizer.config;

import com.fortyeight.tool.relationalvisualizer.dto.SimpleDataSourceInfo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "bootstrap.datasource")
public class BootstrapDataSourceInfo extends SimpleDataSourceInfo {
}
