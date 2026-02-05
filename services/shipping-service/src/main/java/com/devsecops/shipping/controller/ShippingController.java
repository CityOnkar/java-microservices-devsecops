package com.devsecops.shipping.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shippings")
public class ShippingController {

  @GetMapping
  public String ship() {
    return "Shipping Service Running";
  }
}
