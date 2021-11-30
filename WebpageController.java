package com.metric.performance.controller;

import com.metric.performance.model.Webpage;
import com.metric.performance.repository.WebpageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/")
public class WebpageController {

    @Autowired
    private WebpageRepository webpageRepository;

    @GetMapping("metric")
    public List<Webpage> findAll() {
        return webpageRepository.findAll();
    }
}