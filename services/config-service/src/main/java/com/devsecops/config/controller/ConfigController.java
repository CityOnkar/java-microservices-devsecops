package com.devsecops.config.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/configs")
public class ConfigController {

  @GetMapping
  public String config() {
    return "Config Service Running";
  }
}
