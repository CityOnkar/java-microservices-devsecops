package com.devsecops.auth.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auths")
public class AuthController {

  @GetMapping
  public String auth() {
    return "Auth Service Running";
  }
}
