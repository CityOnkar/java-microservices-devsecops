package com.devsecops.analytics.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

  @GetMapping
  public String getAnalytics() {
    return "Analytics Service Running";
  }
}
