package com.devsecops.payment.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

  @GetMapping
  public String pay() {
    return "Payment Service Running";
  }
}
