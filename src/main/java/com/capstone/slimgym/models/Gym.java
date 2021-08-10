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

    public Gym() {
    }

    public Gym(String name, String address, String description, String equipment) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.equipment = equipment;
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
}