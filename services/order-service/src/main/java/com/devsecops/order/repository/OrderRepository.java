package com.devsecops.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.devsecops.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
