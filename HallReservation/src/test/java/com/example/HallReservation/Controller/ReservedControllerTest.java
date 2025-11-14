package com.example.HallReservation.Controller;

import com.example.HallReservation.Dto.ReservedDto;
import com.example.HallReservation.Dto.Status;
import com.example.HallReservation.Entity.Hall;
import com.example.HallReservation.Entity.Professor;
import com.example.HallReservation.Respository.HallRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservedControllerTest {
    @Autowired
    private HallRepository hallRepository;
    @Autowired
    private TestRestTemplate testRestTemplate;
    private final String baseurl= "/reserve";

     private static Long id;


    @Test
    @Order(1)
    public void bookAHall() {
        Hall hall=new Hall();

        hall.setName("l");
        hall.setCapacity(700);
        hall.setPrice(700);
        hall.setStatus(Status.Available);
        hallRepository.save(hall);
        id= hall.getId();
        Hall h = hallRepository.findById(id).orElseThrow();
        Professor p = new Professor();
        p.setName("Priyanka");
        p.setEmail("dgjs");
        ReservedDto reservedDto = new ReservedDto();
        reservedDto.setHall(h);
        reservedDto.setCapacity(15);
        reservedDto.setProfessor(p);
        reservedDto.setStartDate(LocalDateTime.parse("2029-08-20T08:00:00"));
        reservedDto.setEndDate(LocalDateTime.parse("2029-09-20T08:00:00"));
        ResponseEntity<?> response = testRestTemplate.postForEntity(baseurl + "/"+id, reservedDto, String.class);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());


    }

    @Test
    @Order(2)
    public void getBooking() {
        ResponseEntity<List<ReservedDto>> response = testRestTemplate.exchange(
                baseurl + "/booking",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ReservedDto>>() {
                } // Specify the exact return type
        );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getBookingHallBetween() {
        ResponseEntity<List<ReservedDto>> response = testRestTemplate.exchange(
                baseurl + "/between",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ReservedDto>>() {
                }
        );
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
@Test
@Order(4)
    public void cancelBooking() {
ResponseEntity<?>response= testRestTemplate.getForEntity(baseurl+"/cancel/"+id,String.class);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}