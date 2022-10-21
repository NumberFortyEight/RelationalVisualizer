package com.fortyeight.tool.relationalvisualizer.controller;

import com.fortyeight.tool.relationalvisualizer.dto.SimpleDataSourceInfo;
import com.fortyeight.tool.relationalvisualizer.service.dataSourceRouting.DataSourceContextHolderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping()
    public List<String> getDataSourceNames() {
        return dataSourceContextHolderService.getDataSourceNames();
    }

    @PostMapping("{dataSourceName}")
    public void switchDataSource(@PathVariable String dataSourceName) {
        dataSourceContextHolderService.switchTo(dataSourceName);
    }
}
