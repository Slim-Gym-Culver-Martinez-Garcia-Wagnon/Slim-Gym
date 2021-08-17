package com.capstone.slimgym.repositories;

import com.capstone.slimgym.models.Gym;
import com.capstone.slimgym.models.Schedule;
import com.capstone.slimgym.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>{
        List<Schedule> findAllByGymId(Long gymId);
        List<Schedule> findAllByGymUser(User user);


}
