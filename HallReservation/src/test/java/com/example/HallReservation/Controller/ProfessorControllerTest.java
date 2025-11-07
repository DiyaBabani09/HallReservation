package com.example.HallReservation.Controller;

import com.example.HallReservation.Dto.ProfessorDto;
import com.example.HallReservation.Entity.Professor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProfessorControllerTest {
@Autowired
    private RestTemplate restTemplate;

@LocalServerPort
private int port;
private  String baseurl;
    @BeforeEach
    public void setUp() {
        this.baseurl = "http://localhost:" + port + "/api/professor";
    }

    @Test
public void CreateProfessor(){
    ProfessorDto professorDto=new ProfessorDto();
    professorDto.setName("piya");
    professorDto.setEmail("piya.com");
    ResponseEntity<ProfessorDto>professorDtoResponseEntity=restTemplate.postForEntity(baseurl,professorDto,ProfessorDto.class);
    assertEquals(HttpStatus.CREATED, professorDtoResponseEntity.getStatusCode());
    assertNotNull(professorDtoResponseEntity.getBody());
    assertEquals("Jiya",professorDtoResponseEntity.getBody().getName());

}
@Test
public void getProfessor(){
        ResponseEntity<ProfessorDto> response=restTemplate.getForEntity(baseurl,ProfessorDto.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    assertNotNull(response.getBody());
}
@Test
public void updateProfessor(){
    ProfessorDto professorDto=new ProfessorDto();
    professorDto.setName("piaa");
    professorDto.setEmail("piya.com");
        ResponseEntity<ProfessorDto>response=restTemplate.exchange(baseurl+"/17", HttpMethod.PUT, new org.springframework.http.HttpEntity<>(professorDto), ProfessorDto.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("piya", response.getBody().getName());
}
@Test
public void deleteProfessor(){

    ResponseEntity<String> response = restTemplate.exchange(
            baseurl+"/19",
            HttpMethod.DELETE,
            null,
            String.class
    );
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    assertNull(response.getBody());
}


@Test
public void getProfessorById(){
    ResponseEntity<ProfessorDto> response=restTemplate.getForEntity(baseurl+"/4",ProfessorDto.class);
    assertEquals(HttpStatus.OK,response.getStatusCode());


}

}
