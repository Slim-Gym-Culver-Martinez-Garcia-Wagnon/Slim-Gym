package com.capstone.slimgym.controllers;

import com.capstone.slimgym.models.Gym;
import com.capstone.slimgym.models.Review;
import com.capstone.slimgym.models.Schedule;
import com.capstone.slimgym.models.User;
import com.capstone.slimgym.repositories.PostRepository;
import com.capstone.slimgym.repositories.ReviewRepository;
import com.capstone.slimgym.repositories.ScheduleRepository;
import com.capstone.slimgym.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
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

    @GetMapping("/posts/create")
    public String showCreateForm(Model model) {
        model.addAttribute("post", new Gym());
        return "gym/add-gym";
    }

    @PostMapping("/posts/create")
    public String createPost(@ModelAttribute Gym gym) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        gym.setUser(user);
        postDao.save(gym);
        return "redirect:/posts";
    }

    @GetMapping("/posts/{id}")
    public String singlePost(@PathVariable long id, Model model) {
        Gym gym = postDao.getById(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        List<Schedule> schedules = scheduleDao.findAllByGymId(id);
        List<Review> reviews = reviewDao.findAllByGymId(id);
        boolean isPostOwner = false;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            isPostOwner = currentUser.getId() == gym.getUser().getId();
        }
        model.addAttribute("gyms", gym);
        model.addAttribute("user", user);
        model.addAttribute("schedule", new Schedule());
        model.addAttribute("reviews", reviews);
        model.addAttribute("isPostOwner", isPostOwner);

        return "gym/gym-page";
    }

    @PostMapping("/posts/{gym_id}")
    public String singlePost(@PathVariable long gym_id, @ModelAttribute Schedule schedule, BindingResult result, Model model) {
        Gym gymFromDb = postDao.getById(gym_id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        schedule.setId((long) Listie.size() + 1);
        schedule.setGym(gymFromDb);
        schedule.setUser(user);
        System.out.println(schedule.getDate());
        for (Schedule scheduleLoop : scheduleDao.findAll()){
            //Checks to see if the user selected date is equal to any scheduled sessions and if the gym id's of the schedules match
            if(schedule.getDate().compareTo(scheduleLoop.getDate()) == 0 && schedule.getGym().getId() == scheduleLoop.getGym().getId()) {
                System.out.println("Nice");
                // if selected start time or end time is the same as a scheduled session
                if(schedule.getStart_time().equals(scheduleLoop.getStart_time()) || schedule.getEnd_time().equals(scheduleLoop.getEnd_time())){
                    result.hasErrors();
                }
                //if the start time is before a scheduled start time and the end time is after a scheduled end time
                if(Double.parseDouble(schedule.getStart_time()) < Double.parseDouble(scheduleLoop.getStart_time()) && Double.parseDouble(schedule.getEnd_time()) > Double.parseDouble(scheduleLoop.getEnd_time())){
                    result.hasErrors();
                }
                // if the start time is before a scheduled start time and the end time is before a scheduled end time
                if(Double.parseDouble(schedule.getStart_time()) < Double.parseDouble(scheduleLoop.getStart_time()) && Double.parseDouble(schedule.getEnd_time()) < Double.parseDouble(scheduleLoop.getEnd_time())){
                    result.hasErrors();
                }
                // if the start time is after a scheduled start time and the end time if before a scheduled end time
                if(Double.parseDouble(schedule.getStart_time()) > Double.parseDouble(scheduleLoop.getStart_time()) && Double.parseDouble(schedule.getEnd_time()) < Double.parseDouble(scheduleLoop.getEnd_time())){
                    result.hasErrors();
                }
                else {
                    System.out.println("Also nice");
                    scheduleDao.save(schedule);

                }
            }
        }
        return "redirect.posts/" + gym_id;
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

    @GetMapping("/posts/{id}/review-create")
    public String showReview(@PathVariable long id, Model model){
        Gym gym = postDao.getById(id);
        model.addAttribute("gyms", gym);
        model.addAttribute("review", new Review());
        return "gym/create-review";
    }

    @PostMapping("/posts/{id}/review-create")
    public String createReview(@ModelAttribute Review review, @PathVariable long id){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Gym gym = postDao.getById(id);
        review.setUser(user);
        review.setGym(gym);
        reviewDao.save(review);
        return "redirect:/posts/" + id;
    }

    @PostMapping("/review/{id}/delete")
    public String deleteReview(@PathVariable long id) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Review review = reviewDao.getById(id);
        if (currentUser.getId() == review.getUser().getId()) {
            reviewDao.delete(review);
        }
        return "redirect:/gym-page";
    }
}