package com.example.HallReservation.Dto;

public class ProfessorDto {
    long id;
    private String name;
    private String email;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
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

    public ProfessorDto(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public ProfessorDto() {
    }
}
