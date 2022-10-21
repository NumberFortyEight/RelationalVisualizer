package com.fortyeight.tool.relationalvisualizer.service;

import com.fortyeight.tool.relationalvisualizer.advice.exception.DataSourceInfoException;
import com.fortyeight.tool.relationalvisualizer.dto.SimpleDataSourceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SimpleDataSourceInfoValidator {
    private final List<String> availableServerNames;

    public SimpleDataSourceInfoValidator(@Qualifier("availableServerNames") List<String> availableServerNames) {
        this.availableServerNames = availableServerNames;
    }

    public void validate(SimpleDataSourceInfo simpleDataSourceInfo) {
        boolean isServiceNameValid = availableServerNames.contains(simpleDataSourceInfo.getServerName().trim());
        if (!isServiceNameValid)
            throw new DataSourceInfoException("invalid server name. available - %s".formatted(availableServerNames));
    }
}
