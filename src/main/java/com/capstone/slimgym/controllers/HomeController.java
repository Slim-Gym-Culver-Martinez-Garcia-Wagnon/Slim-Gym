package com.capstone.slimgym.controllers;

import com.capstone.slimgym.models.Gym;
import com.capstone.slimgym.models.User;
import com.capstone.slimgym.repositories.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.expression.Lists;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller

public class HomeController {
    private UserRepository users;
    private PostRepository postDao;
    private ReviewRepository reviewDao;
    private PasswordEncoder passwordEncoder;
    private ScheduleRepository scheduleDao;
    private PictureRepository pictureDao;

    public HomeController(UserRepository users, PostRepository postDao, PictureRepository pictureDao) {
        this.users = users;
        this.postDao = postDao;
        this.pictureDao = pictureDao;
    }

    @GetMapping("/")
    public String homepage(){
        return "home";
    }

    @GetMapping("/search")
    public String getSearchForm() {
        if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        return "partials/navbar";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam(name = "search-params") String params, @RequestParam (name = "query") String query) {
        List<Gym> searchResults;
        switch (params) {
            case ("2"):
                searchResults = postDao.findAllByNameContaining(query);
                for (Gym result : searchResults) {
                    System.err.println(result.getName());
                }
                model.addAttribute("searchResults", searchResults);
                model.addAttribute("picture", pictureDao.findAll());
                break;
            case ("3"):
                searchResults = new ArrayList<>();
                List<User> userList = users.findAllByUsernameContaining(query);
                for (User user : userList) {
                    searchResults.addAll(postDao.findAllByUserId(user.getId()));
                }
                model.addAttribute("searchResults", searchResults);
                model.addAttribute("picture", pictureDao.findAll());
                break;
            case ("4"):
                searchResults = postDao.findAllByAddress(query);
                for (Gym result : searchResults) {
                    System.err.println(result.getAddress());
                }
                model.addAttribute("searchResults", searchResults);
                model.addAttribute("picture", pictureDao.findAll());
                break;
            default:
                searchResults = postDao.findAllByAddress(query);
                List<User> usersMaster = users.findAllByUsernameContaining(query);
                for (User user : usersMaster) {
                    searchResults.addAll(postDao.findAllByUserId(user.getId()));
                }
                Set<Gym> holdUp = new HashSet<>(searchResults);
                searchResults = new ArrayList<>(holdUp);
                model.addAttribute("searchResults", searchResults);
                model.addAttribute("pictures", pictureDao.findAll());
                break;
        }
        return "searchresults";
    }

    @GetMapping("/search/results")
    public String showResults(@ModelAttribute(name = "searchResults") ArrayList<Gym> searchResults) {
        if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        return "searchresults";
    }

    @GetMapping("/aboutus")
    public String aboutUs(){
        return "aboutus";
    }
}
