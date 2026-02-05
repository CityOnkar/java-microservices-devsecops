package com.devsecops.inventory.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventorys")
public class InventoryController {

  @GetMapping
  public String inventory() {
    return "Inventory Service Running";
  }
}
