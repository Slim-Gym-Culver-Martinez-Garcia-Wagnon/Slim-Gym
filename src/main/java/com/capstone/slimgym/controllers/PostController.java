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
    public String singlePost(@PathVariable long gym_id, @ModelAttribute Schedule schedule) {
        Gym gymFromDb = postDao.getById(gym_id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        List <Schedule> Listie = scheduleDao.findAll();
//        schedule.setId((long) Listie.size() + 1);
        System.out.println(schedule.getId());
        schedule.setGym(gymFromDb);
        System.out.println(schedule.getId());
        schedule.setUser(user);
        boolean scheduleError;
        for(Schedule currentSchedule : scheduleDao.findAll()){
            if(schedule.getDate().equals(currentSchedule.getDate())){
                System.out.println(schedule.getDate());
                System.out.println("DateDate");
                boolean start
                //User selected Start time
                String[] startTime = schedule.getStart_time().split(":");
                String startHours = startTime[0];
                String startMinutes = startTime[1];
                //User selected End time
                String[] endTime = schedule.getStart_time().split(":");
                String endHours = startTime[0];
                String endMinutes = startTime[1];
                //Scheduled event Start time
                String[] loopStartTime = currentSchedule.getStart_time().split(":");
                String loopStartHours = startTime[0];
                String loopStartMinutes = startTime[1];
                //Scheduled event End time
                String[] loopEndTime = currentSchedule.getEnd_time().split(":");
                String loopEndHours = startTime[0];
                String loopEndMinutes = startTime[1];

                System.out.println(Integer.parseInt(endHours));

                if(Integer.parseInt(endHours) > Integer.parseInt(loopEndHours) || Integer.parseInt(startHours) > Integer.parseInt(loopStartHours)){

                }
            }
        }
        scheduleDao.save(schedule);
        return "redirect:/posts";
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
}
