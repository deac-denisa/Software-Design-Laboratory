package com.example.travelagency.model;

import jakarta.persistence.Entity;

import java.time.LocalDate;



@Entity
public class Cruise extends Vacation {

  public Cruise(String name, String description, String location, double price, LocalDate startDate, LocalDate endDate, int availableSeats) {
    super(name, VacationType.CRUISE, description, location, price, startDate, endDate, availableSeats);
  }

  public Cruise() {
  }
}
