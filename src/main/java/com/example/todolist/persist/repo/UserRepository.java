package com.example.todolist.persist.repo;

import com.example.todolist.persist.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> getByUsername(String username);
    boolean existsByUsername(String username);
}
