package com.example.HallReservation.Service;

import com.example.HallReservation.Dto.HallDto;
import com.example.HallReservation.Dto.ProfessorDto;
import com.example.HallReservation.Dto.Status;
import com.example.HallReservation.Entity.Hall;
import com.example.HallReservation.Entity.Professor;
import com.example.HallReservation.Exception.HallHandlerException;
import com.example.HallReservation.Respository.HallRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.any;

@ExtendWith(MockitoExtension.class)
public class HallServiceTest {
    @Mock
    private HallRepository hallRepository;
    @InjectMocks
    private HallService hallService;
    static Hall h= new Hall();
    @BeforeAll
    public static   void setup(){
        h.setName("K");
        h.setPrice(2000.90);
        h.setCapacity(259);
        h.setStatus(Status.Available);
    }
    public HallDto convertEntityToDto(HallDto hallDto){
        hallDto.setName(h.getName());
        hallDto.setCapacity(h.getCapacity());
        hallDto.setId(h.getId());
        hallDto.setPrice(h.getPrice());
        return hallDto;

    }
    @Test
    public void createHall(){
        HallDto hallDto=new HallDto();
        hallDto=convertEntityToDto(hallDto);
        Mockito.when(hallRepository.save(ArgumentMatchers.any(Hall.class))).thenReturn(h);
        HallDto addedhallDto=hallService.createHalls(hallDto);
        Assertions.assertEquals("K",addedhallDto.getName());
        Assertions.assertNotNull(addedhallDto);
    }
    @Test
    public void updateHall(){
  long id=h.getId();
  h.setName("L");
  h.setPrice(600.00);
  Mockito.when(hallRepository.findById(id)).thenReturn(Optional.of(h));
  HallDto hallDto=new HallDto();
  hallDto= convertEntityToDto(hallDto);
  Mockito.when(hallRepository.save(ArgumentMatchers.any(Hall.class))).thenReturn(h);
        HallDto addedhallDto=hallService.updateHall(id,hallDto);
        Assertions.assertEquals("L",addedhallDto.getName());
        Assertions.assertNotNull(addedhallDto);
    }
    @Test
    public void updateHallThrowError(){
        long id=h.getId();
        h.setName("L");
        h.setPrice(600.00);
        Mockito.when(hallRepository.findById(id)).thenReturn(Optional.empty());
        HallDto hallDto=new HallDto();
        HallDto hallDto1=convertEntityToDto(hallDto);
       HallHandlerException addedHallDto=Assertions.assertThrows(HallHandlerException.class,()-> {
           hallService.updateHall(id, hallDto1);
       });
       Assertions.assertEquals("id does not exist",addedHallDto.getMessage());

    }
    @Test
    public void deleteHall(){
        long id=h.getId();
        Mockito.when(hallRepository.findById(id)).thenReturn(Optional.of(h));
        Mockito.when(hallRepository.save(ArgumentMatchers.any(Hall.class))).thenReturn(h);
        hallService.deletedHall(id);
       Assertions.assertTrue(h.isDeleted());
    }
    @Test
    public void deleteHallThrowException(){
        long id=h.getId();
        Mockito.when(hallRepository.findById(id)).thenReturn(Optional.empty());
        HallHandlerException hallHandlerException=Assertions.assertThrows(HallHandlerException.class,()->hallService.deletedHall(id));
        Assertions.assertEquals("id not found",hallHandlerException.getMessage());

    }
    @Test
    public void getALlHall(){
        Hall h1=new Hall(800,"M",191,700.00,Status.Available);
        Hall h2=new Hall(880,"N",192,700.00,Status.Available);
        Hall h3=new Hall(890,"o",193,700.00,Status.Available);
        Pageable pageable= PageRequest.of(0, 3);
        List<Hall>hallList=Arrays.asList(h1,h2,h3);
        Page<Hall> page=new PageImpl<>(hallList,pageable,hallList.size());
        Mockito.when(hallRepository.findAll(pageable)).thenReturn(page);
  Page<HallDto>  hallDtoPage= hallService.getAllHall(0,3);
  Assertions.assertEquals(3,hallDtoPage.getTotalElements());
        assertThat(page).isNotNull();
    }
    @Test
    public void getHallById(){
        long id=h.getId();
        Mockito.when(hallRepository.findById(id)).thenReturn(Optional.of(h));
        HallDto hallDto=hallService.getById(id);
        Assertions.assertEquals("K",h.getName());
        Assertions.assertEquals(2000.90,h.getPrice());

    }
    @Test
    public void getHallByIdThrowsException(){
        long id=9;
        Mockito.when(hallRepository.findById(id)).thenReturn(Optional.empty());
        HallHandlerException hallHandlerException=Assertions.assertThrows(HallHandlerException.class,()->hallService.getById(id));
        Assertions.assertEquals("id not found",hallHandlerException.getMessage());

    }
}
