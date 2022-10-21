package com.fortyeight.tool.relationalvisualizer.controller;

import com.fortyeight.tool.relationalvisualizer.dto.SimpleDataSourceInfo;
import com.fortyeight.tool.relationalvisualizer.service.dataSourceRouting.DataSourceContextHolderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("DataSource")
public class DataSourceController {

    private final DataSourceContextHolderService dataSourceContextHolderService;

    @PostMapping
    public SimpleDataSourceInfo save(@RequestBody @Valid SimpleDataSourceInfo simpleDataSourceInfo) {
        dataSourceContextHolderService.set(simpleDataSourceInfo);
        return simpleDataSourceInfo;
    }

    @PostMapping("default")
    public void save() {
        dataSourceContextHolderService.switchToDefault();
    }
}
