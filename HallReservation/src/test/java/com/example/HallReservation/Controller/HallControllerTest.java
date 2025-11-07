package com.example.HallReservation.Controller;


import com.example.HallReservation.Dto.HallDto;
import com.example.HallReservation.Dto.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HallControllerTest {
    @Autowired
    private RestTemplate restTemplate;

    @LocalServerPort
    private int port;
    private String baseurl;

    @BeforeEach
    public void setUp() {
        this.baseurl = "http://localhost:" + port + "/api/hall";
    }

    @Test
    public void createHall() {
        HallDto hallDto = new HallDto(700, 0, "F", 400.90, Status.Available);
        ResponseEntity<HallDto> hallDtoResponseEntity = restTemplate.postForEntity(baseurl, hallDto, HallDto.class);
        Assertions.assertEquals(HttpStatus.CREATED, hallDtoResponseEntity.getStatusCode());
    }

    @Test
    public void getAllHalls() {
        ResponseEntity<HallDto> response = restTemplate.getForEntity(baseurl, HallDto.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
//        Assertions.assertEquals(700,response.getBody().getCapacity());
    }

    @Test
    public void getHallById() {
        ResponseEntity<HallDto> response = restTemplate.getForEntity(baseurl + "/10", HallDto.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("F", response.getBody().getName());
    }

    @Test
    public void updateHall() {
        HallDto hallDto = new HallDto();
        hallDto.setName("G");
        hallDto.setCapacity(600);
        hallDto.setPrice(2000.00);
        hallDto.setStatus(Status.Available);
        HttpEntity<HallDto> responseEntity = new HttpEntity<>(hallDto);
        ResponseEntity<HallDto> response = restTemplate.exchange(baseurl + "/10", HttpMethod.PUT, responseEntity, HallDto.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(600, response.getBody().getCapacity());

    }

    @Test
    public void deleteHall() {
        ResponseEntity<Void> response = restTemplate.exchange(baseurl + "/10", HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNull(response.getBody());
    }
}
