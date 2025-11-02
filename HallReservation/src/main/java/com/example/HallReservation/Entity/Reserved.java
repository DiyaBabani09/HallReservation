package com.example.HallReservation.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
@Entity
public class Reserved {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
@ManyToOne
    @JoinColumn(name = "hall_id")
    private Hall hall;
private int capacity;

 @ManyToOne
    @JoinColumn(name="professor_id")
    private Professor professor;

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Reserved(LocalDateTime endDate, Hall hall, Professor professor, int id, LocalDateTime startDate, int capacity) {
        this.endDate = endDate;
        this.hall = hall;
        this.professor = professor;
        this.id = id;
        this.startDate = startDate;
        this.capacity = capacity;
    }

    public Reserved() {
    }
}
