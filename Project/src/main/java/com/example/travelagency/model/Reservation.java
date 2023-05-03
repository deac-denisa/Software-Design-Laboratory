package com.example.travelagency.model;

import jakarta.persistence.*;

@Entity
public class Reservation {
    private Long id;
    private Client client;
    private Vacation vacation;

    public Reservation() {
    }

    public Reservation(Long id, Client client, Vacation vacation) {
        this.id = id;
        this.client = client;
        this.vacation = vacation;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToOne
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @OneToOne
    public Vacation getVacation() {
        return vacation;
    }

    public void setVacation(Vacation vacation) {
        this.vacation = vacation;
    }
}
