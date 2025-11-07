package com.example.HallReservation.Controller;

import com.example.HallReservation.Dto.HallDto;
import com.example.HallReservation.Dto.ReservedDto;
import com.example.HallReservation.Service.ReservedService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
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
    public ResponseEntity<?> Booking(@PathVariable long id, @RequestBody ReservedDto reservedDto){
        reservedService.BookAHall(id,reservedDto);
 return ResponseEntity.status(HttpStatus.CREATED).body("Done");

    }
    @GetMapping("/booking")
    public ResponseEntity<List<ReservedDto>>getBookingHall(){
       return ResponseEntity.ok(reservedService.getBookingDetails());
    }

    @GetMapping("/between")
    public ResponseEntity<List<ReservedDto>>BookingHallBetweenDate(@RequestParam(defaultValue = "2025-11-05T00:00:00") LocalDateTime startDate,
                                                                   @RequestParam(defaultValue = "2025-11-21T00:00:00")   LocalDateTime endDate){
        return ResponseEntity.ok(reservedService.RangeWiseDetails(startDate,endDate));
    }

    @GetMapping("/cancel/{id}")
    public ResponseEntity<String>CancelBooking(@PathVariable int id){
        reservedService.cancelBooking(id);
        return ResponseEntity.ok("Booking is Cancelled");
    }
}
