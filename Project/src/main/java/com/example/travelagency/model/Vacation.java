package com.example.travelagency.model;

import jakarta.persistence.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDate;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Vacation {

    @XmlElement(name = "id")
    private Long id;
    @Enumerated(EnumType.STRING)
    @XmlElement(name = "type")
    private VacationType type;
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "description")
    private String description;
    @XmlElement(name = "location")
    private String location;
    @XmlElement(name = "price")
    private double price;
    @XmlElement(name = "startDate")
    private LocalDate startDate;
    @XmlElement(name = "endDate")
    private LocalDate endDate;
    @XmlElement(name = "availableSeats")
    private int availableSeats;

    public Vacation() {
    }

    public Vacation(String name, VacationType type, String description, String location, double price, LocalDate startDate, LocalDate endDate, int availableSeats) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.location = location;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.availableSeats = availableSeats;

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

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}
