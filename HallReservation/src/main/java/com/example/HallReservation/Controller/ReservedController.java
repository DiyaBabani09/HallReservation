package com.example.HallReservation.Controller;

import com.example.HallReservation.Dto.HallDto;
import com.example.HallReservation.Dto.ReservedDto;
import com.example.HallReservation.Entity.Reserved;
import com.example.HallReservation.Respository.ResevedRepository;
import com.example.HallReservation.Service.ReservedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reserve")
public class ReservedController {
    private final ReservedService reservedService;

    public ReservedController(ReservedService reservedService) {
  this.reservedService=reservedService;
    }
    @GetMapping
    public ResponseEntity<List<HallDto>> getAvailableHall(){
      List<HallDto> hallDtoList= reservedService.reserveHall();
      return ResponseEntity.ok(hallDtoList);

    }
    @PostMapping("/{id}")
    public String Booking(@PathVariable long id, @RequestBody ReservedDto reservedDto){
        reservedService.BookAHall(id,reservedDto);
        return "done";

    }
}
