package com.capstone.slimgym.repositories;

import com.capstone.slimgym.models.Gym;
import com.capstone.slimgym.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Gym, Long> {

    Gym findById(long id);

    Gym findByUser(User user);

    Gym findByName(String name);

    List <Gym> findAllByNameContaining(String name);
    List <Gym> findAllByUserId(Long id);

    List <Gym> findAllByAddress(String address);
}
