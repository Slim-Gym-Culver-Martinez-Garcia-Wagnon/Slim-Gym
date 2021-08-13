package com.capstone.slimgym.repositories;

import com.capstone.slimgym.models.Gym;
import com.capstone.slimgym.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByGymId(long gymId);
}
