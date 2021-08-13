package com.capstone.slimgym.repositories;

import com.capstone.slimgym.models.Gym;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Gym, Long> {
    Gym findById(long id);
    Gym findByName(String name);
}
