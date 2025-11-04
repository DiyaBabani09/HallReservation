package com.example.HallReservation.Respository;

import com.example.HallReservation.Entity.Reserved;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ReservedRepository extends JpaRepository<Reserved,Integer> {
boolean existsByHallIdAndStartDateBetween(long id,LocalDateTime startDate, LocalDateTime endDate);
    @Query(value = "from Reserved r where startDate BETWEEN :startDate AND :endDate")
    List<Reserved> getAllBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate")LocalDateTime endDate);
}
