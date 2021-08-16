package com.capstone.slimgym.models;
import javax.persistence.*;

import java.util.List;
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, length = 240)
    private String first_name;
    @Column(nullable = false, length = 240)
    private String last_name;
    @Column(nullable = false, length = 240)
    private String username;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(length = 255)
    private String profile_pic;
    @Column(nullable = false, length = 240)
    private String emergency_first_name;
    @Column(nullable = false, length = 240)
    private String emergency_last_name;
    @Column(nullable = false)
    private long emergency_phone_number;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Gym> gyms;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Review> reviews;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Schedule> schedules;

    public User() {
    }

    public User(User copy){
        id = copy.id;
        first_name = copy.first_name;
        last_name = copy.last_name;
        email = copy.email;
        username = copy.username;
        password = copy.password;
        profile_pic = copy.profile_pic;
        gyms = copy.gyms;
        reviews = copy.reviews;
        schedules = copy.schedules;
        emergency_first_name = copy.emergency_first_name;
        emergency_last_name = copy.emergency_last_name;
        emergency_phone_number = copy.emergency_phone_number;
    }

    public User(long id, String first_name , String last_name, String username, String email, String password) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(long id, String username, String email, String password, List<Gym> gyms, List<Review> reviews) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.gyms = gyms;
        this.reviews = reviews;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getEmergency_first_name() {
        return emergency_first_name;
    }

    public void setEmergency_first_name(String emergency_first_name) {
        this.emergency_first_name = emergency_first_name;
    }

    public String getEmergency_last_name() {
        return emergency_last_name;
    }

    public void setEmergency_last_name(String emergency_last_name) {
        this.emergency_last_name = emergency_last_name;
    }

    public long getEmergency_phone_number() {
        return emergency_phone_number;
    }

    public void setEmergency_phone_number(long emergency_phone_number) {
        this.emergency_phone_number = emergency_phone_number;
    }

    public List<Gym> getGyms() {
        return gyms;
    }

    public void setGyms(List<Gym> gyms) {
        this.gyms = gyms;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}