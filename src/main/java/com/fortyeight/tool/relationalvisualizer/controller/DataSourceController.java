package com.fortyeight.tool.relationalvisualizer.controller;

import com.fortyeight.tool.relationalvisualizer.config.DataSourceContextHolder;
import com.fortyeight.tool.relationalvisualizer.dto.SimpleDataSourceInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("DataSource")
public class DataSourceController {

    private final DataSourceContextHolder dataSourceContextHolder;

    @PostMapping
    public SimpleDataSourceInfo save(@RequestBody SimpleDataSourceInfo simpleDataSourceInfo) {
        dataSourceContextHolder.set(simpleDataSourceInfo);
        return simpleDataSourceInfo;
    }
}
