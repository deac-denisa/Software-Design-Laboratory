package com.example.travelagency.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Stay extends Vacation {
    private List<SightseeingTrip> sightseeingTrips;

    public Stay() {

    }

    @OneToMany
    public List<SightseeingTrip> getSightseeingTrips() {
        return sightseeingTrips;
    }

    public void setSightseeingTrips(List<SightseeingTrip> sightseeingTrips) {
        this.sightseeingTrips = sightseeingTrips;
    }

    public Stay(String name, VacationType type, String description, String location, double price, LocalDate startDate, LocalDate endDate, int availableSeats) {
        super(name, VacationType.STAY, description, location, price, startDate, endDate, availableSeats);
    }
}
