package com.devsecops.order.service;

import org.springframework.stereotype.Service;
import java.util.List;
import com.devsecops.order.entity.Order;
import com.devsecops.order.repository.OrderRepository;

@Service
public class OrderService {

  private final OrderRepository repo;

  public OrderService(OrderRepository repo) {
    this.repo = repo;
  }

  public List<Order> getAll() {
    return repo.findAll();
  }

  public Order save(Order order) {
    return repo.save(order);
  }
}
