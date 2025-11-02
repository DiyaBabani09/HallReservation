package com.example.HallReservation.Controller;

import com.example.HallReservation.Dto.HallDto;

import com.example.HallReservation.Service.HallService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hall")
public class HallController {
    private final HallService hallService;

    public HallController(HallService hallService) {
        this.hallService = hallService;
    }

@PostMapping
    public ResponseEntity<HallDto> createHall(@RequestBody HallDto hallDto){
    return ResponseEntity.ok(hallService.createHalls(hallDto));

    }
    @PutMapping("/{id}")
    public ResponseEntity<HallDto>updateHall(@PathVariable int id,@RequestBody HallDto hallDto){
        return ResponseEntity.ok(hallService.updateHall(id,hallDto));
    }
    @GetMapping
    public ResponseEntity<Page<HallDto>>getAllHalls(@RequestParam(defaultValue = "0")int pageNo,
                                                    @RequestParam(defaultValue = "2")int pageSize){
        return ResponseEntity.ok(hallService.getAllHall(pageNo,pageSize));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteHall(@PathVariable long id){
        hallService.deletedHall(id);
        return ResponseEntity.ok("Deleted");
    }
    @GetMapping("/{id}")
    public ResponseEntity<HallDto>getHall(@PathVariable long id){
        return ResponseEntity.ok(hallService.getById(id));
    }
}
