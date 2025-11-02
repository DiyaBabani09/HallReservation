package com.example.HallReservation.Respository;

import com.example.HallReservation.Entity.Hall;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HallRepository extends JpaRepository<Hall,Long> {
    List<Hall> findByStatus(String status);
}
