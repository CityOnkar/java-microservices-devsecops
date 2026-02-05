package com.devsecops.user.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.devsecops.user.entity.User;
import com.devsecops.user.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @GetMapping
  public List<User> getUsers() {
    return service.getAll();
  }

  @PostMapping
  public User create(@RequestBody User user) {
    return service.save(user);
  }
}
