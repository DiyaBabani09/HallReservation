package com.example.HallReservation.Service;

import com.example.HallReservation.Dto.ReservedDto;
import com.example.HallReservation.Dto.Status;
import com.example.HallReservation.Entity.Hall;
import com.example.HallReservation.Entity.Professor;
import com.example.HallReservation.Entity.Reserved;
import com.example.HallReservation.Exception.HallHandlerException;
import com.example.HallReservation.Exception.ReservationHandlerException;
import com.example.HallReservation.Respository.HallRepository;
import com.example.HallReservation.Respository.ProfessorRepository;
import com.example.HallReservation.Respository.ReservedRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ReservedServiceTest {
    @Mock
    private ReservedRepository reservedRepository;

    @Mock
    private HallRepository hallRepository;

    @Mock
    private ProfessorRepository professorRepository;

    @InjectMocks
    private ReservedService reservedService;
    Hall h = new Hall();
    Professor professor = new Professor();
    Reserved reserved = new Reserved();

    @BeforeEach
    public void setUp() {
        h.setId(1);
        h.setStatus(Status.Available);
        h.setCapacity(300);
        h.setPrice(400);

        professor.setId(100);
        professor.setName("Diya");
        professor.setEmail("diya@gmail");


        reserved.setId(101);
        reserved.setHall(h);
        reserved.setCapacity(30);
        reserved.setEndDate(LocalDateTime.parse("2029-08-20T08:00:00"));
        reserved.setStartDate(LocalDateTime.parse("2029-09-20T08:00:00"));
        reserved.setProfessor(professor);
    }

    public ReservedDto convertEntityToDto(Reserved reserved) {
        ReservedDto reservedDto = new ReservedDto();
        reservedDto.setId(reserved.getId());
        reservedDto.setHall(reserved.getHall());
        reservedDto.setCapacity(reserved.getCapacity());
        reservedDto.setProfessor(reserved.getProfessor());
        reservedDto.setStartDate(reserved.getStartDate());
        reservedDto.setEndDate(reserved.getEndDate());
        return reservedDto;
    }

    @Test
    public void bookAHall() {
        Mockito.when(hallRepository.findById(h.getId())).thenReturn(Optional.of(h));
        Mockito.when(professorRepository.findById(professor.getId())).thenReturn(Optional.of(professor));
        Reserved r = new Reserved();
        r.setId(101);
        r.setHall(h);
        r.setCapacity(30);
        r.setEndDate(LocalDateTime.parse("2029-08-20T08:00:00"));
        r.setStartDate(LocalDateTime.parse("2029-09-20T08:00:00"));
        r.setProfessor(professor);
        r.setStatus(Status.Confirmed);
        h.setStatus(Status.Booked);
        Mockito.when(reservedRepository.existsByHallIdAndStartDateBetween(h.getId(), r.getStartDate(), r.getEndDate())).thenReturn(false);
        ReservedDto reservedDto = convertEntityToDto(r);
        Mockito.when(reservedRepository.save(any(Reserved.class))).thenReturn(r);
        reservedService.BookAHall(1, reservedDto);
        Mockito.verify(reservedRepository, Mockito.times(1)).save(any(Reserved.class));

    }

    @Test
    public void bookAHallThrowsHallNotFoundException() {
        long invalidId = 900;
        Mockito.when(hallRepository.findById(invalidId)).thenReturn(Optional.empty());
        ReservedDto reservedDto = new ReservedDto();
        HallHandlerException response = Assertions.assertThrows(HallHandlerException.class, () -> reservedService.BookAHall(invalidId, reservedDto));
        Assertions.assertEquals("Id not found", response.getMessage());

    }

    @Test
    public void bookAHallThrowsNotAvailableFoundException() {
        Mockito.when(hallRepository.findById(h.getId())).thenReturn(Optional.of(h));

        Reserved r = new Reserved();
        Mockito.when(reservedRepository.existsByHallIdAndStartDateBetween(h.getId(), r.getStartDate(), r.getEndDate())).thenReturn(true);
        ReservedDto reservedDto = new ReservedDto();

        HallHandlerException response = Assertions.assertThrows(HallHandlerException.class, () -> reservedService.BookAHall(1, reservedDto));
        Assertions.assertEquals("In that time hall is already booked,change time and date", response.getMessage());

    }

    @Test
    public void getBookingDetails() {

        Reserved r = new Reserved();
        r.setId(101);
        r.setHall(h);
        r.setCapacity(30);
        r.setEndDate(LocalDateTime.parse("2029-08-20T08:00:00"));
        r.setStartDate(LocalDateTime.parse("2029-09-20T08:00:00"));
        r.setProfessor(professor);
        r.setStatus(Status.Confirmed);
        h.setStatus(Status.Booked);
        Reserved r1 = new Reserved();
        r.setId(102);
        r.setHall(h);
        r.setCapacity(130);
        r.setEndDate(LocalDateTime.parse("2029-08-20T08:00:00"));
        r.setStartDate(LocalDateTime.parse("2029-09-20T08:00:00"));
        r.setProfessor(professor);
        r.setStatus(Status.Confirmed);
        h.setStatus(Status.Booked);
        List<Reserved> reservedList = List.of(r1, r);


        Mockito.when(reservedRepository.findAll()).thenReturn(reservedList);
        List<ReservedDto> reservedListResponse = reservedService.getBookingDetails();
        Assertions.assertNotNull(reservedListResponse);

    }

    @Test
    public void RangeWiseDetails() {
        LocalDateTime startDate = LocalDateTime.parse("2029-01-20T08:00:00");
        LocalDateTime endDate = LocalDateTime.parse("2029-10-20T09:00:00");
        Reserved r = new Reserved();
        r.setId(101);
        r.setHall(h);
        r.setCapacity(30);
        r.setEndDate(LocalDateTime.parse("2029-01-20T08:00:00"));
        r.setStartDate(LocalDateTime.parse("2029-03-20T08:00:00"));
        r.setProfessor(professor);
        r.setStatus(Status.Confirmed);
        h.setStatus(Status.Booked);
        Reserved r1 = new Reserved();
        r.setId(102);
        r.setHall(h);
        r.setCapacity(130);
        r.setEndDate(LocalDateTime.parse("2029-05-20T08:00:00"));
        r.setStartDate(LocalDateTime.parse("2029-09-20T08:00:00"));
        r.setProfessor(professor);
        r.setStatus(Status.Confirmed);
        h.setStatus(Status.Booked);
        List<Reserved> reservedList = List.of(r1, r);
        Mockito.when(reservedRepository.getAllBetweenDates(startDate, endDate)).thenReturn(reservedList);
        List<ReservedDto> reservedDtoList = reservedService.RangeWiseDetails(startDate, endDate);
        Assertions.assertNotNull(reservedDtoList);
    }

    @Test
    public void cancelBooking() {
        int id = 5;
        Mockito.when(reservedRepository.findById(id)).thenReturn(Optional.of(reserved));
        reservedService.cancelBooking(id);
        Mockito.verify(reservedRepository, Mockito.times(1)).save(any(Reserved.class));
        Assertions.assertEquals(Status.Cancelled, reserved.getStatus());
    }

    @Test
    public void cancelBookingThrowsException() {
        int id = 5;
        Mockito.when(reservedRepository.findById(id)).thenReturn(Optional.empty());
        ReservationHandlerException response = Assertions.assertThrows(ReservationHandlerException.class, () -> reservedService.cancelBooking(id));
        Mockito.verify(reservedRepository, Mockito.never()).save(any(Reserved.class));
        Assertions.assertEquals("Any Reservation is not found", response.getMessage());
    }
}
