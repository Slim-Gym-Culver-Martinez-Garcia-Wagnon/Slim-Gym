package com.capstone.slimgym.controllers;

import com.capstone.slimgym.models.*;
import com.capstone.slimgym.repositories.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {
    private final PostRepository postDao;
    private final UserRepository userDao;
    private final ReviewRepository reviewDao;
    private final ScheduleRepository scheduleDao;
    private final PictureRepository pictureDao;

    public PostController(PostRepository postDao, UserRepository userDao, ReviewRepository reviewDao, ScheduleRepository scheduleDao, PictureRepository pictureDao) {
        this.postDao = postDao;
        this.userDao = userDao;
        this.reviewDao = reviewDao;
        this.scheduleDao = scheduleDao;
        this.pictureDao = pictureDao;
    }


    @GetMapping("/gyms")
    public String viewPosts(Model model) {
        model.addAttribute("gyms", postDao.findAll());
        model.addAttribute("pictures", pictureDao.findAll());
        return "index";
    }

    @GetMapping("/gym/create")
    public String showCreateForm(Model model) {
        model.addAttribute("post", new Gym());
        model.addAttribute("picture", new Picture());
        return "gym/add-gym";
    }

    @PostMapping("/gym/create")
    public String createPost(@RequestParam(name = "fileupload") ArrayList<String> urls, @ModelAttribute Gym gym) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        gym.setUser(user);
        postDao.save(gym);
        User userfromDB = userDao.findById(user.getId());
        Gym newgym = userfromDB.getGyms().get(userfromDB.getGyms().size()-1);
        for(String url : urls){
            if(!url.isEmpty()){
                Picture picture = new Picture();
                picture.setGym(newgym);
                picture.setUrl(url);
                pictureDao.save(picture);
            }
        }

//        System.out.println(picture.getUrl());
//        System.out.println(picture.getGym());

        return "redirect:/gyms";
    }

    @GetMapping("/gym/{id}")
    public String singlePost(@PathVariable long id, Model model) {
        Gym gym = postDao.findById(id);
//        List<Schedule> schedules = scheduleDao.findAllByGymId(id);
        List<Review> reviews = reviewDao.findAllByGymId(id);
        boolean isPostOwner = false;

        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            isPostOwner = currentUser.getId() == gym.getUser().getId();
        }
        //Comment
        model.addAttribute("gyms", gym);
        model.addAttribute("schedule", new Schedule());
        model.addAttribute("reviews", reviews);
        model.addAttribute("isPostOwner", isPostOwner);
        model.addAttribute("pictures", gym.getPictures());

        return "gym/gym-page";
    }

    @PostMapping("/posts/{gym_id}")
    public String singlePost(@PathVariable long gym_id, @ModelAttribute @Valid Schedule schedule, Model model) {
        Gym gymFromDb = postDao.getById(gym_id);
        List<Review> reviews = reviewDao.findAllByGymId(gym_id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isPostOwner = false;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            isPostOwner = currentUser.getId() == gymFromDb.getUser().getId();
        }
        schedule.setGym(gymFromDb);
        schedule.setUser(user);

        //User selected Start time
        String[] startTime = schedule.getStart_time().split(":");
        String startHours = startTime[0];
        String startMinutes = startTime[1];

        //User selected End time
        String[] endTime = schedule.getEnd_time().split(":");
        String endHours = endTime[0];
        String endMinutes = endTime[1];

        model.addAttribute("gyms", gymFromDb);
        model.addAttribute("user", user);
        model.addAttribute("reviews", reviews);
        model.addAttribute("pictures", postDao.findAll());
        model.addAttribute("isPostOwner", isPostOwner);

        boolean scheduleError = false;
        for(Schedule currentSchedule : scheduleDao.findAllByGymId(gym_id)){
            //Scheduled event Start time
            String[] loopStartTime = currentSchedule.getStart_time().split(":");
            String loopStartHours = loopStartTime[0];
            String loopStartMinutes = loopStartTime[1];
            //Scheduled event End time
            String[] loopEndTime = currentSchedule.getEnd_time().split(":");
            String loopEndHours = loopEndTime[0];
            String loopEndMinutes = loopEndTime[1];
            if (currentSchedule.getDate().equals(schedule.getDate())){
                System.out.println("Dates match, will not save");
                if(Integer.parseInt(startHours) == Integer.parseInt(loopStartHours)){
                    if(Integer.parseInt(startMinutes) > Integer.parseInt(loopStartMinutes)){
                        System.out.println("these start times match 2");
                        System.out.println(startHours + ":" + startMinutes);
                        System.out.println(endHours + ":" + endMinutes);
                        System.out.println(loopStartHours + ":" + loopStartMinutes);
                        System.out.println(loopEndHours + ":" + loopEndMinutes);
                        scheduleError = true;
                        model.addAttribute("error", scheduleError);
                        return "gym/gym-page";
                    }

                }
                if(Integer.parseInt(startHours) == Integer.parseInt(loopEndHours)){
                    if(Integer.parseInt(startMinutes) < Integer.parseInt(loopEndMinutes)){
                        System.out.println("these start times match 2");
                        System.out.println(startHours + ":" + startMinutes);
                        System.out.println(endHours + ":" + endMinutes);
                        System.out.println(loopStartHours + ":" + loopStartMinutes);
                        System.out.println(loopEndHours + ":" + loopEndMinutes);
                        scheduleError = true;
                        model.addAttribute("error", scheduleError);
                        return "gym/gym-page";
                    }

                }
                else if(Integer.parseInt(endHours) == Integer.parseInt(loopEndHours)){
                    if(Integer.parseInt(endMinutes) < Integer.parseInt(loopEndMinutes)){
                        System.out.println("these end times match 1");
                        System.out.println(endHours + ":" + endMinutes);
                        System.out.println(loopEndHours + ":" + loopEndMinutes);
                        scheduleError = true;
                        model.addAttribute("error", scheduleError);
                        return "gym/gym-page";
                    }
                }
                else if(Integer.parseInt(endHours) == Integer.parseInt(loopStartHours)){
                    if(Integer.parseInt(endMinutes) > Integer.parseInt(loopStartMinutes)){
                        System.out.println("these end times match 2");
                        System.out.println(endHours + ":" + endMinutes);
                        System.out.println(loopEndHours + ":" + loopEndMinutes);
                        scheduleError = true;
                        model.addAttribute("error", scheduleError);
                        return "gym/gym-page";
                    }
                }
                else if(Integer.parseInt(endHours) > Integer.parseInt(loopStartHours) && Integer.parseInt(startHours) < Integer.parseInt(loopEndHours)){
                    System.out.println("Times overlap");
                    System.out.println(endHours + ":" + endMinutes);
                    System.out.println(loopStartHours + ":" + loopStartMinutes);
                    scheduleError = true;
                    model.addAttribute("error", scheduleError);
                    return "gym/gym-page";
                }
            }
        }
        scheduleDao.save(schedule);
        return "redirect:/profile";
    }


    @GetMapping("/gym/{id}/edit")
    public String editForm(@PathVariable long id, Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Gym gym = postDao.getById(id);
        if (currentUser.getId() == gym.getUser().getId()) {
            model.addAttribute("post", gym);
            return "gym/edit-gym";
        } else {
            return "redirect:/posts/" + id;
        }
    }

    @PostMapping("/posts/{id}/edit/update")
    public String editPost(@PathVariable long id, @ModelAttribute Gym gym) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Gym gymFromDB = postDao.getById(id);
        if (user.getId() == gymFromDB.getUser().getId()) {
            gym.setUser(user);
            postDao.save(gym);
        }
        return "redirect:/gym/" + id;
    }

    @PostMapping("/gym/{id}/delete")
    public String deletePost(@PathVariable long id) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Gym gym = postDao.getById(id);
        if (currentUser.getId() == gym.getUser().getId()) {
            postDao.delete(gym);
        }
        return "redirect:/profile";
    }
}
