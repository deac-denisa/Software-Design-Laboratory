package com.labactivitytracking.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Teacher {
    private long id;
    private String name;
    private String email;
    private String password;
    private List<Laboratory> labs;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToMany
    public List<Laboratory> getLabs() {
        return labs;
    }

    public void setLabs(List<Laboratory> labs) {
        this.labs = labs;
    }
}
