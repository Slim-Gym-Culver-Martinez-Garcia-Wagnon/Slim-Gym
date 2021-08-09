package com.capstone.slimgym.repositories;

import com.capstone.slimgym.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findById(long id);
    Post findByTitle(String title);
}
