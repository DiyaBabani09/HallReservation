package com.example.HallReservation.Service;

import com.example.HallReservation.Dto.HallDto;
import com.example.HallReservation.Dto.ReservedDto;
import com.example.HallReservation.Entity.Hall;
import com.example.HallReservation.Entity.Reserved;
import com.example.HallReservation.Exception.HallHandlerException;
import com.example.HallReservation.Respository.HallRepository;
import com.example.HallReservation.Respository.ResevedRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservedService {
    private final ResevedRepository resevedRepository;
    private final HallRepository hallRepository;

    public ReservedService(ResevedRepository resevedRepository,  HallRepository hallRepository) {
        this.resevedRepository = resevedRepository;
        this.hallRepository = hallRepository;

    }
    public List<HallDto> reserveHall(){
List<Hall> hall=hallRepository.findByStatus("available");
        List<HallDto>hallDtoList=new ArrayList<>();
        for(Hall h:hall){
            HallDto hallDto1=new HallDto();
            hallDto1.setId(h.getId());
            hallDto1.setName(h.getName());
            hallDto1.setCapacity(h.getCapacity());
            hallDto1.setPrice(h.getPrice());
            hallDto1.setStatus(h.getStatus());
            hallDtoList.add(hallDto1);
        }
return hallDtoList;
    }
    public void BookAHall(long id, ReservedDto reserved){
        Hall h=hallRepository.findById(id).orElseThrow(()->new HallHandlerException("Id not found"));
        if(!h.getStatus().equals("available")){
            System.out.println(h.getStatus());
            throw new HallHandlerException("Hall is not available");
        }
        if(h.getCapacity()<reserved.getCapacity()){
            System.out.println(reserved.getCapacity());
            System.out.println(h.getCapacity());
            throw  new HallHandlerException("Hall capacity is less then required capacity");
        }
List<Hall> hall= resevedRepository.findByHallIdAndStartDateBetween(id,reserved.getStartDate(),reserved.getEndDate());

    if(!hall.isEmpty()){
         throw new HallHandlerException("In that time hall is already booked,change time");
    }


Reserved r=new Reserved();
r.setHall(h);
r.setCapacity(reserved.getCapacity());
r.setEndDate(reserved.getEndDate());
r.setStartDate(reserved.getStartDate());
r.setProfessor(reserved.getProfessor());
Reserved re=resevedRepository.save(r);

    }
}
