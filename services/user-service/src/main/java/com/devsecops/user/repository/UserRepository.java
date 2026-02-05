package com.devsecops.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.devsecops.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
