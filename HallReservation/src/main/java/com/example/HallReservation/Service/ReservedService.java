package com.example.HallReservation.Service;

import com.example.HallReservation.Dto.HallDto;
import com.example.HallReservation.Dto.ProfessorDto;
import com.example.HallReservation.Dto.ReservedDto;
import com.example.HallReservation.Entity.Hall;
import com.example.HallReservation.Entity.Professor;
import com.example.HallReservation.Entity.Reserved;
import com.example.HallReservation.Exception.HallHandlerException;
import com.example.HallReservation.Exception.ProfessorHandleException;
import com.example.HallReservation.Respository.HallRepository;
import com.example.HallReservation.Respository.ProfessorRepository;
import com.example.HallReservation.Respository.ReservedRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservedService {
    private final ReservedRepository resevedRepository;
    private final HallRepository hallRepository;
    private final ProfessorRepository professorRepository;
    private final ProfessorService professorService;

    public ReservedService(ReservedRepository reservedRepository, HallRepository hallRepository, ProfessorRepository professorRepository, ProfessorService professorService) {
        this.resevedRepository = reservedRepository;
        this.hallRepository = hallRepository;

        this.professorRepository = professorRepository;
        this.professorService = professorService;
    }

    public List<HallDto> reserveHall() {
        List<Hall> hall = hallRepository.findByStatus("available");
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
        if (resevedRepository.existsByHallIdAndStartDateBetween(id, reserved.getStartDate(), reserved.getEndDate())) {
            throw new HallHandlerException("In that time hall is already booked,change time and date");
        }
//
        if (h.getCapacity() < reserved.getCapacity()) {
            System.out.println(reserved.getCapacity());
            System.out.println(h.getCapacity());
            throw new HallHandlerException("Hall capacity is less then required capacity");
        }

        Optional<Professor> professor= professorRepository.findById(reserved.getProfessor().getId());
        if(professor.isEmpty()) {
            Professor p=reserved.getProfessor();
            ProfessorDto professorDto1=new ProfessorDto();
            professorDto1.setId(p.getId());
            professorDto1.setName(p.getName());
            professorDto1.setEmail(p.getEmail());
            professorService.saveProfessor(professorDto1);

      }
        Reserved r = new Reserved();
        r.setHall(h);
        r.setCapacity(reserved.getCapacity());
        r.setEndDate(reserved.getEndDate());
        r.setStartDate(reserved.getStartDate());
        r.setProfessor(reserved.getProfessor());
        h.setStatus("Booked");
        System.out.println(h.getStatus());
        Reserved re = resevedRepository.save(r);

    }

    public List<ReservedDto> getBookingDetails() {
        List<Reserved> r = resevedRepository.findAll();
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
        List<Reserved> r = resevedRepository.getAllBetweenDates(startDate, endDate);
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
}