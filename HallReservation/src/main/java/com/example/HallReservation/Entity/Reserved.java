package com.example.HallReservation.Entity;

import com.example.HallReservation.Dto.Status;
import jakarta.persistence.*;

import java.time.LocalDateTime;

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

@Enumerated(EnumType.STRING)
private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

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



    public Reserved() {
    }
}
