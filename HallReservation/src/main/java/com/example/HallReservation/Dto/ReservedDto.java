package com.example.HallReservation.Dto;

import com.example.HallReservation.Entity.Hall;
import com.example.HallReservation.Entity.Professor;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class ReservedDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private Hall hall;
    private int capacity;


    private Professor professor;

    public ReservedDto(int capacity, Hall hall, LocalDateTime endDate, int id, LocalDateTime startDate, Professor professor) {
        this.capacity = capacity;
        this.hall = hall;
        this.endDate = endDate;
        this.id = id;
        this.startDate = startDate;
        this.professor = professor;
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



    public ReservedDto() {
    }
}
