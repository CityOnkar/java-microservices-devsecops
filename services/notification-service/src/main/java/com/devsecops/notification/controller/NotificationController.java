package com.devsecops.notification.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

  @GetMapping
  public String notification() {
    return "Notification Service Running";
  }
}
