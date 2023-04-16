package com.labactivitytracking.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Entity
public class Laboratory {
    private long id;
    private int weekNumber;
    private String title;
    private LocalDate date;
    private String curricula;
    private String description;
    private List<Student> attendance;
    private Assignment assignment;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCurricula() {
        return curricula;
    }

    public void setCurricula(String curricula) {
        this.curricula = curricula;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToMany
    public List<Student> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<Student> attendance) {
        this.attendance = attendance;
    }

    @OneToOne
    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    @Override
    public String toString() {
        String result = "Laboratory{" +
                "id=" + id +
                ", weekNumber=" + weekNumber +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", curricula='" + curricula + '\'' +
                ", description='" + description + '\'' +
                ", assignment=" + assignment.getId() +
                ", attendance ids=";
        for(Student s: attendance){
            result += s.getId() + ", ";
        }
        return  result + '}';
    }
}
