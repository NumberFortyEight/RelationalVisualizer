package com.fortyeight.tool.relationalvisualizer.controller;

import com.fortyeight.tool.relationalvisualizer.dto.Entry;
import com.fortyeight.tool.relationalvisualizer.service.EntryPointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("EntryPoint")
public class EntryPointController {
    private final EntryPointService entryPointService;

    @GetMapping("{table}")
    private List<Entry> getByTable(@PathVariable String table) {
        return entryPointService.getByTable(table);
    }
}
