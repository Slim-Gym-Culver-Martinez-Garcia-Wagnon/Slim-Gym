package com.capstone.slimgym.controllers;

import com.capstone.slimgym.models.*;
import com.capstone.slimgym.repositories.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import javax.validation.Valid;
import java.util.List;


@Controller
public class UserController {

    private final UserRepository users;
    private final PostRepository postDao;
    private final ReviewRepository reviewDao;
    private final PasswordEncoder passwordEncoder;
    private final ScheduleRepository scheduleDao;
    private final PictureRepository pictureDao;

    public UserController(UserRepository users, PasswordEncoder passwordEncoder, ReviewRepository reviewDao, PostRepository postDao, ScheduleRepository scheduleDao, PictureRepository pictureDao) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.reviewDao = reviewDao;
        this.postDao = postDao;
        this.scheduleDao = scheduleDao;
        this.pictureDao = pictureDao;
    }

    @GetMapping("/sign-up")
    public String showSignupForm(Model model){
        model.addAttribute("user", new User());
        return "user/sign-up";
    }

    @PostMapping("/sign-up")
    public String saveUser(@Valid User user, BindingResult result){
        if (result.hasErrors()) {
            return "user/sign-up";
        }
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        users.save(user);;
        return "redirect:/login";
    }


    @GetMapping("/user/{id}/edit")
    public String userToEdit(@PathVariable long id, Model model) {
//        checks to see if the user is logged in and has authentication
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = user.getId();
        if(id == userId){
            model.addAttribute("user", users.getById(id));
            model.addAttribute("id", id);
            return "user/editUser";
        }
        else{
            return "user/login";
        }
    }


    @PostMapping("/user/edit/update/{id}")
    public String editUser(@PathVariable long id, @ModelAttribute User user) {

        User updateUser = users.findById(user.getId());
        user.setFirst_name(updateUser.getFirst_name());
        user.setLast_name(updateUser.getLast_name());
        user.setUsername(updateUser.getUsername());
        user.setId(id);
        if(user.getPassword().isEmpty()){
            user.setPassword(updateUser.getPassword());
        }else{
            String hash = passwordEncoder.encode(user.getPassword());
            user.setPassword(hash);
        }
        users.save(user);
        return "redirect:/profile";

    }

    @GetMapping("/profile")
    public String userProfile(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Gym> gyms = postDao.findAllByUserId(user.getId());
        User userOwner = users.findById(user.getId());
        List<Review> reviews = reviewDao.findByUserId(user.getId());
        List<Picture> gymPictures = pictureDao.findAll();
        List<Schedule> schedules = scheduleDao.findAll();
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser"){
            return "redirect:/login";
        }

        model.addAttribute("gym_pictures", gymPictures);
        model.addAttribute("reviews", reviews);
        model.addAttribute("schedules", schedules);
        model.addAttribute("gyms", gyms);
        model.addAttribute("events", scheduleDao.findAllByGymUser(user));
        model.addAttribute("user", userOwner);
        model.addAttribute("isOwner", false);
        return "user/profile";
    }

}
