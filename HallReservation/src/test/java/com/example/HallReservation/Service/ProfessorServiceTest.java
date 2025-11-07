package com.example.HallReservation.Service;

import com.example.HallReservation.Dto.ProfessorDto;
import com.example.HallReservation.Entity.Professor;
import com.example.HallReservation.Exception.ProfessorHandleException;
import com.example.HallReservation.Respository.ProfessorRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProfessorServiceTest {
    @Mock
    ProfessorRepository professorRepository;
    @InjectMocks
    ProfessorService professorService;

    @Test
    void myTest() {
        System.out.println("example only");
    }

    @BeforeAll
    public static void init() {
        System.out.println("class level ,run before class");
        System.out.println("Before all test");
    }
    Professor p = new Professor();
    @BeforeEach
    public void beforeEach(){
        System.out.println("Before Each");
        p.setId(1);
        p.setName("Diya");
        p.setEmail("fg");
    }

    @Test
    void convertEntityToDto() {

        Professor p = new Professor();
        p.setId(1);
        p.setName("Diya");
        p.setEmail("fg");
        ProfessorDto professorDto1 = new ProfessorDto();
        professorDto1.setId(p.getId());
        professorDto1.setName(p.getName());
        professorDto1.setEmail(p.getEmail());
        Assertions.assertEquals(p.getId(), professorDto1.getId());

    }

    ProfessorDto convertEntityToDto(Professor professor) {
        ProfessorDto professorDto = new ProfessorDto();
        professorDto.setId(professor.getId());
        professorDto.setName(professor.getName());
        professorDto.setEmail(professor.getEmail());
        return professorDto;
    }

    @Test
    public void saveProfessorTest() {



        ProfessorDto professorDto1 = convertEntityToDto(p);

        Mockito.when(professorRepository.save(any(Professor.class)))
                .thenReturn(p);
        ProfessorDto addedprofessorDto = professorService.saveProfessor(professorDto1);
        Assertions.assertNotNull(professorDto1);
        Assertions.assertEquals(professorDto1.getName(), addedprofessorDto.getName());
        Assertions.assertEquals(1, addedprofessorDto.getId());
    }

    @Test
    public void deleteProfessorShouldDeleteSuccessfully() {
//        Mockito.doNothing().when(professorRepository).deleteById(2L);
//        assertThrows(ProfessorHandleException.class,()->professorService.deleteProfessor(2));
//        Mockito.verify(professorRepository,Mockito.times(1)).deleteById(2L);
        Professor p = new Professor();
//        p.setId(1);
        p.setDeleted(false);
        Mockito.when(professorRepository.findById(2L)).thenReturn(Optional.of(p));
        Mockito.when(professorRepository.save(any(Professor.class))).thenReturn(p);
        professorService.deleteProfessor(2L);
        Mockito.verify(professorRepository, Mockito.times(1)).findById(2L);

        Mockito.verify(professorRepository, Mockito.times(1)).save(p);
        assertTrue(p.isDeleted());

    }

    @Test
    public void deleteProfessorShouldThrowExceptionWhenIdNotFound() {
        long invalidId = 3;
        Mockito.when(professorRepository.findById(invalidId)).thenReturn(Optional.empty());
        assertThrows(ProfessorHandleException.class, () -> professorService.deleteProfessor(invalidId));

        Mockito.verify(professorRepository, Mockito.times(1)).findById(invalidId);

        Mockito.verify(professorRepository, Mockito.never()).save(any(Professor.class));
    }

    @Test
    public void updateProfessorTest() {
        long id = 1;
        Professor p = new Professor();
        p.setName("Diya");
        p.setEmail("fg");
        Mockito.when(professorRepository.findById(id)).thenReturn(Optional.of(p));
        ProfessorDto professorDto1 = convertEntityToDto(p);
        Mockito.when(professorRepository.save(any(Professor.class))).thenReturn(p);
        ProfessorDto addedProfessorDto = professorService.updateProfessor(professorDto1, id);
        assertEquals(professorDto1.getName(), addedProfessorDto.getName());
        assertEquals(professorDto1.getEmail(), addedProfessorDto.getEmail());


    }

    @Test
    public void updateProfessorTestShouldThrowException() {
        long id = 1;
        Professor p = new Professor();
        p.setName("Diya");
        p.setEmail("fg");
        ProfessorDto professorDto1 = convertEntityToDto(p);
        Mockito.when(professorRepository.findById(id)).thenReturn(Optional.empty());
        ProfessorHandleException addedProfessorDto = assertThrows(ProfessorHandleException.class, () -> professorService.updateProfessor(professorDto1, id));
        assertEquals("Id does not exist", addedProfessorDto.getMessage());
        Mockito.verify(professorRepository, Mockito.never()).save(any(Professor.class));


    }
    @Test
    public void getAllProfessorTest() {
        int pageNo = 0;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Professor p1 = new Professor(1L, "Diya", "12@gmail", false);
        Professor p2 = new Professor(2L, "Moni", "diya@12", true);
        Professor p3 = new Professor(3L, "Priya", "priya@gmail.com", false);
        List<Professor> mockProfessors = Arrays.asList(p1, p2, p3);
        Page<Professor> page = new PageImpl<>(mockProfessors, pageable, mockProfessors.size());
        Mockito.when(professorRepository.findAll(pageable)).thenReturn(page);
        Page<ProfessorDto> professorDtoPage = professorService.getProfessor(pageNo, pageSize);
        Mockito.verify(professorRepository, Mockito.times(1)).findAll(pageable);
        assertThat(page).isNotNull();
        assertThat(page.getTotalElements()).isEqualTo(3);
    }
    @AfterAll
    public static void cleanAfterClass(){
        System.out.println("after class");
        System.out.println("after all class run");
    }
}
