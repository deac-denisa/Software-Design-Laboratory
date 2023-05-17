package com.example.travelagency.model;


import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class Tour extends Vacation {

    public Tour(String name, VacationType type, String description, String location, double price, LocalDate startDate, LocalDate endDate, int availableSeats) {
        super(name, VacationType.TOUR, description, location, price, startDate, endDate, availableSeats);
    }

    public Tour() {

    }
}