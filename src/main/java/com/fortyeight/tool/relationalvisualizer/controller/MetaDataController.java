package com.fortyeight.tool.relationalvisualizer.controller;

import com.fortyeight.tool.relationalvisualizer.service.MetaDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("MetaData")
public class MetaDataController {

    private final MetaDataService service;

    @GetMapping
    public List<String> getTableNames() {
        return service.getTableNames();
    }

}
