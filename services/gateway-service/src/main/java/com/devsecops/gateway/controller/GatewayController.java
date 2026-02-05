package com.devsecops.gateway.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gateways")
public class GatewayController {

  @GetMapping
  public String gateway() {
    return "Gateway Service Running";
  }
}
