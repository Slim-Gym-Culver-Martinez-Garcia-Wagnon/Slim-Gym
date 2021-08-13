package com.capstone.slimgym.repositories;

import com.capstone.slimgym.models.Review;
import com.capstone.slimgym.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByUser(User user);
    List<Review> findByUserId(long id);

    List<Review> findAllByGymId(Long gymId);

}
