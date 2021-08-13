package com.capstone.slimgym.controllers;

import com.capstone.slimgym.models.Gym;
import com.capstone.slimgym.models.Review;
import com.capstone.slimgym.models.Schedule;
import com.capstone.slimgym.models.User;
import com.capstone.slimgym.repositories.PostRepository;
import com.capstone.slimgym.repositories.ReviewRepository;
import com.capstone.slimgym.repositories.ScheduleRepository;
import com.capstone.slimgym.repositories.UserRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
public class PostController {
    private final PostRepository postDao;
    private final UserRepository userDao;
    private final ReviewRepository reviewDao;
    private final ScheduleRepository scheduleDao;

    public PostController(PostRepository postDao, UserRepository userDao, ReviewRepository reviewDao, ScheduleRepository scheduleDao) {
        this.postDao = postDao;
        this.userDao = userDao;
        this.reviewDao = reviewDao;
        this.scheduleDao = scheduleDao;
    }


    @GetMapping("/posts")
    public String viewPosts(Model model) {
        model.addAttribute("gyms", postDao.findAll());
        return "index";
    }

    @GetMapping("/posts/{id}")
    public String singlePost(@PathVariable long id, Model model) {
        Gym gym = postDao.getById(id);
        List<Schedule> schedules = scheduleDao.findAllByGymId(id);
        List<Review> reviews = reviewDao.findAllByGymId(id);
        boolean isPostOwner = false;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            isPostOwner = currentUser.getId() == gym.getUser().getId();
        }
        model.addAttribute("gyms", gym);
        model.addAttribute("reviews", reviews);
        model.addAttribute("schedule", new Schedule());
        return "gym-page";
    }

    @PostMapping("/posts/{id}")
    public String singlePost(@PathVariable long id, @ModelAttribute Schedule schedule) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Gym gymFromDb = postDao.getById(id);
        List<Review> reviews = reviewDao.findAllByGymId(id);
        schedule.setGym(gymFromDb);
        schedule.setUser(user);
        scheduleDao.save(schedule);
        return "gym-page";
    }

    @GetMapping("/posts/{id}/edit")
    public String editForm(@PathVariable long id, Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Gym gym = postDao.getById(id);
        if (currentUser.getId() == gym.getUser().getId()) {
            model.addAttribute("post", gym);
            return "posts/edit";
        } else {
            return "redirect:/posts/" + id;
        }
    }

    @PostMapping("/posts/{id}/edit")
    public String editPost(@PathVariable long id, @ModelAttribute Gym gym) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Gym gymFromDB = postDao.getById(id);
        if (user.getId() == gymFromDB.getUser().getId()) {
            gym.setUser(user);
            postDao.save(gym);
        }
        return "redirect:/posts/" + id;
    }

    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable long id) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Gym gym = postDao.getById(id);
        if (currentUser.getId() == gym.getUser().getId()) {
            postDao.delete(gym);
        }
        return "redirect:/posts";
    }

    @GetMapping("/posts/create")
    public String showCreateForm(Model model) {
        model.addAttribute("post", new Gym());
        return "add-gym";
    }

    @PostMapping("/posts/create")
    public String createPost(@ModelAttribute Gym gym) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        gym.setUser(user);
        postDao.save(gym);
        return "redirect:/posts";
    }
}