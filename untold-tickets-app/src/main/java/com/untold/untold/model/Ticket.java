package com.untold.untold.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
public class Ticket {
    private long id;
    private double price;
    private Concert concert;
    private String person;
    private int seats;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @OneToOne(optional = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    public Concert getConcert() {
        return concert;
    }

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    public void setConcert(Concert concert) {
        this.concert = concert;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }


    public String toString2() {
        return "Ticket{" +
                "id=" + id +
                ", price=" + price +
                ", concert=" + concert.getId() + " " + concert.getName() +
                ", person='" + person + '\'' +
                ", seats=" + seats +
                '}';
    }

    @Override
    public String toString() {
        return "" +
                id +
                "," + price +
                ", " + concert.getName() +
                "," + person + '\'' +
                ", " + seats;
    }
}
