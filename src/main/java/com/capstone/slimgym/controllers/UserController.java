package com.capstone.slimgym.controllers;

import com.capstone.slimgym.models.User;
import com.capstone.slimgym.repositories.ReviewRepository;
import com.capstone.slimgym.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private UserRepository users;
    private ReviewRepository reviewDao;
    private PasswordEncoder passwordEncoder;

    public UserController(UserRepository users, PasswordEncoder passwordEncoder, ReviewRepository reviewDao) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.reviewDao = reviewDao;
    }

    @GetMapping("/sign-up")
    public String showSignupForm(Model model){
        model.addAttribute("user", new User());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User user){
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        users.save(user);
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
            return "editUser";
        }
        else{
            return "login";
        }
    }


    @PostMapping("/user/edit/update/{id}")
    public String editUser(@PathVariable long id, @ModelAttribute User user) {
        User updateUser = users.findById(user.getId());
//        user.setUser(updateUser);
        users.save(updateUser);
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String userProfile(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = user.getId();
        model.addAttribute("reviews", reviewDao.findByUserId(userId));
        return "profile";
    }




//        @GetMapping("/user/{id}/profile")
//    public String userProfile(@PathVariable long id, Model model) {
//        User user = users.getById(id);
//        boolean theUser = false;
//            if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
//                User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//                theUser = currentUser.getId() == user.getUsername().findById();
//            }
//            model.addAttribute("user", user);
//            model.addAttribute("theUser", theUser);
//            return "profile";
//    }

}
