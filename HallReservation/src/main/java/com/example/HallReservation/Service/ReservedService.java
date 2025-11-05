package com.example.HallReservation.Service;

import com.example.HallReservation.Dto.HallDto;
import com.example.HallReservation.Dto.ReservedDto;
import com.example.HallReservation.Dto.Status;
import com.example.HallReservation.Entity.Hall;
import com.example.HallReservation.Entity.Professor;
import com.example.HallReservation.Entity.Reserved;
import com.example.HallReservation.Exception.HallHandlerException;
import com.example.HallReservation.Exception.ProfessorHandleException;
import com.example.HallReservation.Exception.ReservationHandlerException;
import com.example.HallReservation.Respository.HallRepository;
import com.example.HallReservation.Respository.ProfessorRepository;
import com.example.HallReservation.Respository.ReservedRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservedService {
    private final ReservedRepository reservedRepository;
    private final HallRepository hallRepository;
    private final ProfessorRepository professorRepository;


    public ReservedService(ReservedRepository reservedRepository, HallRepository hallRepository, ProfessorRepository professorRepository) {
        this.reservedRepository = reservedRepository;
        this.hallRepository = hallRepository;

        this.professorRepository = professorRepository;

    }

    public List<HallDto> reserveHall() {
        List<Hall> hall = hallRepository.findByStatus("Available");
        List<HallDto> hallDtoList = new ArrayList<>();
        for (Hall h : hall) {
            HallDto hallDto1 = new HallDto();
            hallDto1.setId(h.getId());
            hallDto1.setName(h.getName());
            hallDto1.setCapacity(h.getCapacity());
            hallDto1.setPrice(h.getPrice());
            hallDto1.setStatus(h.getStatus());
            hallDtoList.add(hallDto1);
        }
        return hallDtoList;
    }

    public void BookAHall(long id, ReservedDto reserved) {
        Hall h = hallRepository.findById(id).orElseThrow(() -> new HallHandlerException("Id not found"));
        if (reservedRepository.existsByHallIdAndStartDateBetween(id, reserved.getStartDate(), reserved.getEndDate())) {
            throw new HallHandlerException("In that time hall is already booked,change time and date");
        }
//
        if (h.getCapacity() < reserved.getCapacity()) {
            System.out.println(reserved.getCapacity());
            System.out.println(h.getCapacity());
            throw new HallHandlerException("Hall capacity is less then required capacity");
        }
        Professor professor = null;
        if (reserved.getProfessor().getId() > 0) {
            professor = professorRepository.findById(reserved.getProfessor().getId()).orElseThrow(() -> new ProfessorHandleException("id does not exist"));
        }
        if (professor == null) {
            professorRepository.findByEmail(reserved.getProfessor().getEmail()).orElseGet(() -> {
                Professor professor1 = new Professor();

                professor1.setName(reserved.getProfessor().getName());
                professor1.setEmail(reserved.getProfessor().getEmail());
                reserved.setProfessor(professor1);
                return professorRepository.save(professor1);
            });
        }
        Reserved r = new Reserved();
        r.setHall(h);
        r.setCapacity(reserved.getCapacity());
        r.setEndDate(reserved.getEndDate());
        r.setStartDate(reserved.getStartDate());
        r.setProfessor(professor);
        r.setStatus(Status.Confirmed);
        h.setStatus(Status.Booked);
        System.out.println(h.getStatus());
        reservedRepository.save(r);

    }

    public List<ReservedDto> getBookingDetails() {
        List<Reserved> r = reservedRepository.findAll();
        List<ReservedDto> reservedDtoList = new ArrayList<>();
        for (Reserved reserved : r) {
            ReservedDto reservedDto = new ReservedDto();
            reservedDto.setId(reserved.getId());
            reservedDto.setCapacity(reserved.getCapacity());
            reservedDto.setStartDate(reserved.getStartDate());
            reservedDto.setEndDate(reserved.getEndDate());
            reservedDto.setProfessor(reserved.getProfessor());
            reservedDto.setHall(reserved.getHall());
            reservedDtoList.add(reservedDto);
        }
        return reservedDtoList;
    }

    public List<ReservedDto> RangeWiseDetails(LocalDateTime startDate, LocalDateTime endDate) {
        List<Reserved> r = reservedRepository.getAllBetweenDates(startDate, endDate);
        List<ReservedDto> reservedDtoList = new ArrayList<>();
        for (Reserved reserved : r) {
            ReservedDto reservedDto = new ReservedDto();
            reservedDto.setId(reserved.getId());
            reservedDto.setCapacity(reserved.getCapacity());
            reservedDto.setStartDate(reserved.getStartDate());
            reservedDto.setEndDate(reserved.getEndDate());
            reservedDto.setProfessor(reserved.getProfessor());
            reservedDto.setHall(reserved.getHall());
            reservedDtoList.add(reservedDto);
        }
        return reservedDtoList;
    }

    public void cancelBooking(int id) {
        Reserved r = reservedRepository.findById(id).orElseThrow(() -> new ReservationHandlerException("Any Reservation is not found"));
        LocalDateTime localDateTime = r.getEndDate();
        if (localDateTime.isBefore(LocalDateTime.now())) {
            throw new ReservationHandlerException("That reservation is already done,not valid booking");
        }
        r.setStatus(Status.Cancelled);

        reservedRepository.save(r);


    }
}