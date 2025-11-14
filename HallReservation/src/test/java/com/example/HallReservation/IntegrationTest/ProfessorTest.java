package com.example.HallReservation.IntegrationTest;

import com.example.HallReservation.Controller.ProfessorController;
import com.example.HallReservation.Dto.ProfessorDto;
import com.example.HallReservation.Entity.Professor;
import com.example.HallReservation.Respository.ProfessorRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
//@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProfessorTest {
    @Autowired
    private ProfessorController professorController;

private static long id;
    @Test
    @Order(1)
    public void createProfessor(){
        ProfessorDto p=new ProfessorDto();
        p.setName("Varsha");
        p.setEmail("123@7gmail");
        ResponseEntity<ProfessorDto> response=professorController.CreateProfessor(p);
        Assertions.assertEquals(HttpStatus.CREATED,response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("Varsha",response.getBody().getName());
id=response.getBody().getId();

    }
    @Test
    @Order(2)
    public void getAllProfessor(){
        ResponseEntity<Page<ProfessorDto>> response = professorController.getAllProfessor(0,1);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(1,response.getBody().getTotalElements());
    }
    @Test
    @Order(3)
    public void getProfessorById(){
        ResponseEntity<ProfessorDto> response=  professorController.getProfessorById(id);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("123@7gmail",response.getBody().getEmail());
    }
    @Order(4)
    @Test
    public void updateProfessor(){
        ProfessorDto p=new ProfessorDto();
        p.setName("Vanshika");
        p.setEmail("123@7gmail");
        ResponseEntity<ProfessorDto> response= professorController.updatedProfessor(id,p);
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals("Vanshika",response.getBody().getName());
    }
    @Order(5)
    @Test
    public void deleteProfessor(){
     ResponseEntity<?>response=   professorController.deleteProfessor(id);
     Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
     Assertions.assertEquals("deleted successfully",response.getBody());

    }
}

