package com.example.HallReservation.Service;

import com.example.HallReservation.Dto.HallDto;
import com.example.HallReservation.Dto.Status;
import com.example.HallReservation.Entity.Hall;
import com.example.HallReservation.Exception.HallHandlerException;
import com.example.HallReservation.Respository.HallRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HallService {
    private final HallRepository hallRepository;

    public HallService(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }


    public HallDto createHalls(HallDto dto){
        Hall h=new Hall();

     h.setName(dto.getName());
     h.setCapacity(dto.getCapacity());
     h.setPrice(dto.getPrice());
     h.setStatus(Status.Available);
     Hall hall=hallRepository.save(h);
     HallDto hallDto=new HallDto();
     hallDto.setId(h.getId());
     hallDto.setName(hall.getName());
     hallDto.setCapacity(hall.getCapacity());

     hallDto.setPrice(hall.getPrice());
     hallDto.setStatus(Status.Available);
     return hallDto;

    }
    public HallDto updateHall(long id,HallDto hallDto){
      Hall h= hallRepository.findById(id).orElseThrow(()->new HallHandlerException("id does not exist"));

      h.setName(hallDto.getName());
        h.setCapacity(hallDto.getCapacity());
        h.setPrice(hallDto.getPrice());
        Hall hall=hallRepository.save(h);
        HallDto hallDto1= new HallDto();
        hallDto1.setId(hall.getId());
        hallDto1.setName(hall.getName());
        hallDto1.setCapacity(hall.getCapacity());
        hallDto1.setPrice(hall.getPrice());
        hallDto1.setStatus(hall.getStatus());
        return hallDto1;
    }
    public Page<HallDto> getAllHall(int pageNo,int pageSize){
        Pageable pageable= PageRequest.of(pageNo, pageSize);
        Page<Hall> halls=hallRepository.findAll(pageable);
        List<HallDto>hallDtoList=new ArrayList<>();
        for(Hall h:halls) {
            if (!h.isDeleted()) {
                HallDto hallDto1 = new HallDto();
                hallDto1.setId(h.getId());
                hallDto1.setName(h.getName());
                hallDto1.setCapacity(h.getCapacity());
                hallDto1.setPrice(h.getPrice());
                hallDto1.setStatus(h.getStatus());
                hallDtoList.add(hallDto1);
            }
        }
        return  new PageImpl<>(hallDtoList,pageable,halls.getTotalElements());
    }
    public void deletedHall(long id){
      Hall hall=  hallRepository.findById(id).orElseThrow(()->new HallHandlerException("id not found"));
     hall.setDeleted(true);
     hallRepository.save(hall);
    }
    public HallDto getById(long id){
      Hall hall=  hallRepository.findById(id).orElseThrow(()->new HallHandlerException("id not found"));
        HallDto hallDto1;
        hallDto1 = new HallDto();
        hallDto1.setId(hall.getId());
        hallDto1.setName(hall.getName());
        hallDto1.setCapacity(hall.getCapacity());
        hallDto1.setPrice(hall.getPrice());
        hallDto1.setStatus(hall.getStatus());
        return hallDto1;
    }
}
