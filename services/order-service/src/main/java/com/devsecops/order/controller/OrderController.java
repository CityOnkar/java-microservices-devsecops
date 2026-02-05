package com.devsecops.order.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.devsecops.order.entity.Order;
import com.devsecops.order.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

  private final OrderService service;

  public OrderController(OrderService service) {
    this.service = service;
  }

  @GetMapping
  public List<Order> getOrders() {
    return service.getAll();
  }

  @PostMapping
  public Order create(@RequestBody Order order) {
    return service.save(order);
  }
}
