package com.capstone.slimgym.models;

import javax.persistence.*;

@Entity
@Table(name="gym_equipment")
public class Gym_Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "equipment_type_id")
    private Gym_Equipment gym_equipment;

    @ManyToOne
    @JoinColumn(name = "gym_id")
    private Gym gym;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Gym_Equipment getGym_equipment() {
        return gym_equipment;
    }

    public void setGym_equipment(Gym_Equipment gym_equipment) {
        this.gym_equipment = gym_equipment;
    }

    public Gym getGym() {
        return gym;
    }

    public void setGym(Gym gym) {
        this.gym = gym;
    }
}
