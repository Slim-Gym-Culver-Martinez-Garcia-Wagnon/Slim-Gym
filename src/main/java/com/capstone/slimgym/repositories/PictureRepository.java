package com.capstone.slimgym.repositories;

import com.capstone.slimgym.models.Gym;
import com.capstone.slimgym.models.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PictureRepository extends JpaRepository<Picture, Long> {

    List<Picture> findByGym(Gym gym);
}
