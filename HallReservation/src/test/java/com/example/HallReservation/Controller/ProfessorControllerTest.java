package com.example.HallReservation.Controller;

import com.example.HallReservation.Dto.ProfessorDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProfessorControllerTest {
    @Autowired
private TestRestTemplate testRestTemplate;
 String baseurl="/professor";


//    @Test
//public void CreateProfessor(){
//    ProfessorDto professorDto=new ProfessorDto();
//    professorDto.setName("piya");
//    professorDto.setEmail("piya.com");
//    ResponseEntity<ProfessorDto>professorDtoResponseEntity=restTemplate.postForEntity(baseurl,professorDto,ProfessorDto.class);
//    assertEquals(HttpStatus.CREATED, professorDtoResponseEntity.getStatusCode());
//    assertNotNull(professorDtoResponseEntity.getBody());
//    assertEquals("Jiya",professorDtoResponseEntity.getBody().getName());
//
//}
//@Test
//public void getProfessor(){
//        ResponseEntity<ProfessorDto> response=restTemplate.getForEntity(baseurl,ProfessorDto.class);
//        assertEquals(HttpStatus.OK,response.getStatusCode());
//    assertNotNull(response.getBody());
//}
//@Test
//public void updateProfessor(){
//    ProfessorDto professorDto=new ProfessorDto();
//    professorDto.setName("piaa");
//    professorDto.setEmail("piya.com");
//        ResponseEntity<ProfessorDto>response=restTemplate.exchange(baseurl+"/17", HttpMethod.PUT, new org.springframework.http.HttpEntity<>(professorDto), ProfessorDto.class);
//    assertEquals(HttpStatus.OK, response.getStatusCode());
//    assertNotNull(response.getBody());
//    assertEquals("piya", response.getBody().getName());
//}
//@Test
//public void deleteProfessor(){
//
//    ResponseEntity<String> response = restTemplate.exchange(
//            baseurl+"/19",
//            HttpMethod.DELETE,
//            null,
//            String.class
//    );
//    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//
//    assertNull(response.getBody());
//}
//
//
//@Test
//public void getProfessorById(){
//    ResponseEntity<ProfessorDto> response=restTemplate.getForEntity(baseurl+"/4",ProfessorDto.class);
//    assertEquals(HttpStatus.OK,response.getStatusCode());
//
//
//}
private static long id;
    @Test
    @Order(1)
public void CreateProfessor(){
        ProfessorDto professorDto=new ProfessorDto();

    professorDto.setName("Vanshika");
    professorDto.setEmail("vanshika.com");
    ResponseEntity<ProfessorDto>professorDtoResponseEntity=testRestTemplate.postForEntity(baseurl,professorDto,ProfessorDto.class);
    assertEquals(HttpStatus.CREATED, professorDtoResponseEntity.getStatusCode());
    assertNotNull(professorDtoResponseEntity.getBody());
    assertEquals("Vanshika",professorDtoResponseEntity.getBody().getName());
    id=professorDtoResponseEntity.getBody().getId();
        System.out.println(id);

}
@Order(2)
@Test
public void getProfessor(){
        ResponseEntity<ProfessorDto> response=testRestTemplate.getForEntity(baseurl,ProfessorDto.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    assertNotNull(response.getBody());
}

@Test
@Order(4)
public void updateProfessor(){
    ProfessorDto professorDto=new ProfessorDto();
    professorDto.setName("khushi");
    professorDto.setEmail("khushi.com");
        ResponseEntity<ProfessorDto>response=testRestTemplate.exchange(baseurl+"/"+ id, HttpMethod.PUT, new org.springframework.http.HttpEntity<>(professorDto), ProfessorDto.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("khushi", response.getBody().getName());
}
@Test
@Order(5)
public void deleteProfessor(){

    ResponseEntity<String> response =testRestTemplate.exchange(
            baseurl+"/100",
            HttpMethod.DELETE,
            null,
            String.class
    );
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

}


@Test
@Order(3)
public void getProfessorById(){
    ProfessorDto professorDto=new ProfessorDto();
    System.out.println(id);
    ResponseEntity<ProfessorDto> response=testRestTemplate.getForEntity(baseurl+"/"+ id,ProfessorDto.class);
    assertEquals(HttpStatus.OK,response.getStatusCode());



 }

}
