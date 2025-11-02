package com.example.HallReservation.Respository;

import com.example.HallReservation.Entity.Hall;
import com.example.HallReservation.Entity.Reserved;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ResevedRepository extends JpaRepository<Reserved,Integer> {
List<Hall> findByHallIdAndStartDateBetween(long id, LocalDateTime startDate, LocalDateTime endDate);
}
