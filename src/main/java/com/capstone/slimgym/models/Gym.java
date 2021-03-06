package com.capstone.slimgym.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="gym")
public class Gym {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 240)
    private String name;

    @Column(nullable = false, length = 240)
    private String address;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, length = 240)
    private String equipment;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gym")
    private List<Picture> pictures;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gym")
    private List<Review> reviews;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gym")
    private List<Schedule> schedules;

    public Gym() {
    }

    public Gym(String name, String address, String description, String equipment, List<Review> reviews, List<Picture> pictures) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.equipment = equipment;
        this.reviews = reviews;
        this.pictures = pictures;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}