package com.TaskManagement.Repository;

import com.TaskManagement.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    @Override
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
}
