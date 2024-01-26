package com.broughty.advent.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdventController {


    Logger logger = LoggerFactory.getLogger(AdventController.class);

    PluginRegistry<AdventDayPlugin, String> pluginRegistry;


    public AdventController(PluginRegistry<AdventDayPlugin, String> pluginRegistry) {
        this.pluginRegistry = pluginRegistry;
    }

    @GetMapping("/")
    public String adventDefault(Model model) {
        model.addAttribute("adventDays", AdventDay.values());
        return "advent";
    }

    @GetMapping("/advent")
    public String adventChoice(Model model) {
        model.addAttribute("adventDays", AdventDay.values());
        return "advent";
    }

    @PostMapping("/advent")
    public String adventResultsRequest(@RequestParam String adventDay, Model model) {
        logger.info("Post /advent Passed adventDay {}", adventDay);
        AdventDayPlugin adventDayPlugin = pluginRegistry.getPluginFor(adventDay).orElseThrow();
        var adventResult = new AdventDayResults(adventDay, adventDayPlugin.partOne(),
                adventDayPlugin.partTwo(), adventDayPlugin.getAdventUrl());
        logger.info("Advent Result = {}", adventResult);
        model.addAttribute("adventDayResult", adventResult);
        return "result";
    }

    private record AdventDayResults(String adventDay, String partOne, String partTwo, String adventUrl){
    }

}