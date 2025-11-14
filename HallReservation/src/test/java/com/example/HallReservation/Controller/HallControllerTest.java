package com.example.HallReservation.Controller;


import com.example.HallReservation.Dto.HallDto;
import com.example.HallReservation.Dto.Status;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HallControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    private final String baseurl = "/hall";
    private static long id;

    @Test
    @Order(1)
    public void createHall() {
        HallDto hallDto = new HallDto(700, 0, "F", 400.90, Status.Available);
        ResponseEntity<HallDto> hallDtoResponseEntity = testRestTemplate.postForEntity(baseurl, hallDto, HallDto.class);
        Assertions.assertEquals(HttpStatus.CREATED, hallDtoResponseEntity.getStatusCode());
        assertNotNull(hallDtoResponseEntity.getBody());
        id = hallDtoResponseEntity.getBody().getId();
        System.out.println(id);
    }

    @Test
    @Order(2)
    public void getAllHalls() {
        ResponseEntity<HallDto> response = testRestTemplate.getForEntity(baseurl, HallDto.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());

    }

    @Test
    @Order(3)
    public void getHallById() {
        ResponseEntity<HallDto> response = testRestTemplate.getForEntity(baseurl + "/" + id, HallDto.class);
        System.out.println(id);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("F", response.getBody().getName());
    }

    @Test
    @Order(4)
    public void updateHall() {
        HallDto hallDto = new HallDto();
        hallDto.setName("G");
        hallDto.setCapacity(600);
        hallDto.setPrice(2000.00);
        hallDto.setStatus(Status.Available);
        HttpEntity<HallDto> responseEntity = new HttpEntity<>(hallDto);
        ResponseEntity<HallDto> response = testRestTemplate.exchange(baseurl + "/" + id, HttpMethod.PUT, responseEntity, HallDto.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(600, response.getBody().getCapacity());

    }

    @Test
    @Order(5)
    public void deleteHall() {
        ResponseEntity<Void> response = testRestTemplate.exchange(baseurl + "/" + id, HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNull(response.getBody());
    }
}
