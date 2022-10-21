package com.fortyeight.tool.relationalvisualizer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Stream;

@Configuration
public class AvailableServerNamesConfig {

    @Value("${datasource.availableNames}")
    private String[] availableServerNames;

    @Bean("availableServerNames")
    public List<String> availableServerNames() {
        return Stream.of(availableServerNames)
                .map(String::trim)
                .toList();
    }
}
