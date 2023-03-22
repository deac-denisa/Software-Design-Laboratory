package com.untold.untold.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
public class Concert {
    private long id;
    private String name;
    private String description;
    private LocalDate dateAndTime;
    private int maxTickets;
    private List<Band> bands;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(LocalDate dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public int getMaxTickets() {
        return maxTickets;
    }

    public void setMaxTickets(int maxTickets) {
        this.maxTickets = maxTickets;
    }

    @OneToMany
    public List<Band> getBands() {
        return bands;
    }

    public void setBands(List<Band> bands) {
        this.bands = bands;
    }
}
