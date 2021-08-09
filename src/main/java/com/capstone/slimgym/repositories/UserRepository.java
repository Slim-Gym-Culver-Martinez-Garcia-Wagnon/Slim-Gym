package com.capstone.slimgym.repositories;

import com.capstone.slimgym.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    User findById(long id);
    User findByUsername(String username);
}
