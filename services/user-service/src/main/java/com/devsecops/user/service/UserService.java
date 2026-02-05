package com.devsecops.user.service;

import org.springframework.stereotype.Service;
import java.util.List;
import com.devsecops.user.entity.User;
import com.devsecops.user.repository.UserRepository;

@Service
public class UserService {

  private final UserRepository repo;

  public UserService(UserRepository repo) {
    this.repo = repo;
  }

  public List<User> getAll() {
    return repo.findAll();
  }

  public User save(User user) {
    return repo.save(user);
  }
}
