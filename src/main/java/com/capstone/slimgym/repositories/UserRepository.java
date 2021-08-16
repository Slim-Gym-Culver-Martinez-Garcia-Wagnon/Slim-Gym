package com.capstone.slimgym.repositories;

import com.capstone.slimgym.models.Gym;
import com.capstone.slimgym.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>{
    User findById(long id);
    User findByUsername(String username);

    List<User> findAllByUsernameContaining(String name);
}
