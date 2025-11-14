package com.example.HallReservation.IntegrationTest;

import com.example.HallReservation.Controller.ReservedController;
import com.example.HallReservation.Dto.ReservedDto;
import com.example.HallReservation.Dto.Status;
import com.example.HallReservation.Entity.Hall;
import com.example.HallReservation.Entity.Professor;
import com.example.HallReservation.Entity.Reserved;

import com.example.HallReservation.Respository.HallRepository;
import com.example.HallReservation.Respository.ReservedRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservedTest {
    @Autowired
    private ReservedController reservedController;
    @Autowired
    private HallRepository hallRepository;
    @Autowired
    private ReservedRepository reservedRepository;
    private static long hallId = 1;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int id;

    @Test
    @Order(1)
    public void BookingAHall() {
        Hall hall = new Hall();
        hall.setName("K");
        hall.setPrice(900.00);
        hall.setStatus(Status.Available);
        hall.setCapacity(800);
        hallRepository.save(hall);
        hallId = hall.getId();
        Hall h = hallRepository.findById(hallId).orElseThrow();
        Professor p = new Professor();
        p.setName("Priyanka");
        p.setEmail("dgjs");
        ReservedDto reservedDto = new ReservedDto();
        reservedDto.setId(100);
        reservedDto.setHall(h);
        reservedDto.setCapacity(15);
        reservedDto.setProfessor(p);
        reservedDto.setStartDate(LocalDateTime.parse("2029-08-20T08:00:00"));
        reservedDto.setEndDate(LocalDateTime.parse("2029-09-20T08:00:00"));
        ResponseEntity<?> response = reservedController.Booking(hallId, reservedDto);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        List<Reserved> allBookings = reservedRepository.findAll();
        Reserved createdEntityInDB = allBookings.getFirst();
  id = createdEntityInDB.getId();
        startDate = reservedDto.getStartDate();
        endDate = reservedDto.getEndDate();

        System.out.println(id);
    }

    @Test
    @Order(2)
    public void getBooking() {
        ResponseEntity<List<ReservedDto>> response = reservedController.getBookingHall();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response);
    }

    @Test
    @Order(3)
    public void getBookingHallBetween() {
        ResponseEntity<List<ReservedDto>> response = reservedController.BookingHallBetweenDate(startDate, endDate);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response);

    }
}
