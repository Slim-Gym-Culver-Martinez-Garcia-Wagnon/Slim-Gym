package com.capstone.slimgym.controllers;

import com.capstone.slimgym.models.Gym;
import com.capstone.slimgym.models.Review;
import com.capstone.slimgym.models.User;
import com.capstone.slimgym.repositories.PostRepository;
import com.capstone.slimgym.repositories.ReviewRepository;
import com.capstone.slimgym.repositories.ScheduleRepository;
import com.capstone.slimgym.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReviewController {
    private final PostRepository postDao;
    private final UserRepository userDao;
    private final ReviewRepository reviewDao;
    private final ScheduleRepository scheduleDao;

    public ReviewController(PostRepository postDao, UserRepository userDao, ReviewRepository reviewDao, ScheduleRepository scheduleDao) {
        this.postDao = postDao;
        this.userDao = userDao;
        this.reviewDao = reviewDao;
        this.scheduleDao = scheduleDao;
    }

    @GetMapping("/posts/{id}/review-create")
    public String showReview(@PathVariable long id, Model model){
        model.addAttribute("gym_id", id);
        model.addAttribute("review", new Review());
        return "gym/create-review";
    }

    @PostMapping("/posts/{gym_id}/review-create")
    public String createReview(@ModelAttribute Review review, @PathVariable long gym_id, Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Gym gym = postDao.getById(gym_id);
        model.addAttribute("gym_id", gym_id);
        review.setUser(user);
        review.setGym(gym);
        reviewDao.save(review);
        return "redirect:/posts/" + gym_id;
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
