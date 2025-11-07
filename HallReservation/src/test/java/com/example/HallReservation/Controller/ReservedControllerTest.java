package com.example.HallReservation.Controller;

import com.example.HallReservation.Dto.ReservedDto;
import com.example.HallReservation.Entity.Hall;
import com.example.HallReservation.Entity.Professor;
import com.example.HallReservation.Entity.Reserved;
import com.example.HallReservation.Respository.HallRepository;
import com.example.HallReservation.Respository.ProfessorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservedControllerTest {
    @Autowired
    private HallRepository hallRepository;
    @Autowired
    private ProfessorRepository professorRepository;
    @Autowired
    private RestTemplate restTemplate;
    @LocalServerPort
    private int port;
    private String baseurl;

    @BeforeEach
    public void setUp() {
        this.baseurl = "http://localhost:" + port + "/api/reserve";
    }

    @Test
    public void bookAHall() {
        Long id = 1L;
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
        ResponseEntity<?> response;
        response = restTemplate.postForEntity(baseurl + "/1", reservedDto, String.class);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());


    }

    @Test
    public void getBooking() {
        ResponseEntity<List<ReservedDto>> response = restTemplate.exchange(
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
        ResponseEntity<List<ReservedDto>> response = restTemplate.exchange(
                baseurl + "/between",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ReservedDto>>() {
                }
        );
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
@Test
    public void cancelBooking() {
ResponseEntity<?>response=restTemplate.getForEntity(baseurl+"/cancel/31",String.class);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}