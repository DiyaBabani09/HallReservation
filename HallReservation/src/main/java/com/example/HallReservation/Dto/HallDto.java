package com.example.HallReservation.Dto;

import jakarta.persistence.Column;

public class HallDto {
    private long id;
    private String name;
    @Column(unique = true)
    private int capacity;
    private String status;
private Double price;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public HallDto(int capacity, long id, Double price, String status, String name) {
        this.capacity = capacity;
        this.id = id;
        this.price = price;
        this.status = status;
        this.name = name;
    }


    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HallDto() {
    }
}
