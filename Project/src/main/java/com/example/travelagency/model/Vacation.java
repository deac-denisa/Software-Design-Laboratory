package com.example.travelagency.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Vacation {
    private Long id;
    @Enumerated(EnumType.STRING)
    private VacationType type;
    private String name;
    private String description;
    private String location;
    private double price;
    private LocalDate startDate;
    private LocalDate endDate;

    public Vacation() {
    }

    public Vacation(String name, VacationType type, String description, String location, double price, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.location = location;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VacationType getType() {
        return type;
    }

    public void setType(VacationType type) {
        this.type = type;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
