package com.example.HallReservation.Controller;

import com.example.HallReservation.Dto.ProfessorDto;
import com.example.HallReservation.Service.ProfessorService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/professor")
public class ProfessorController {
    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }
    @PostMapping
    public ResponseEntity<ProfessorDto>CreateProfessor(@RequestBody ProfessorDto professorDto){
    ProfessorDto   professor= professorService.saveProfessor(professorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(professor);
    }
    @GetMapping
    public ResponseEntity<Page<ProfessorDto>>getAllProfessor
            (@RequestParam(defaultValue = "0")int pageNo,
             @RequestParam(defaultValue = "2")int pageSize){
        return ResponseEntity.ok(professorService.GetProfessor(pageNo,pageSize));

    }
    @PutMapping("/{id}")
    public ResponseEntity<ProfessorDto>updatedProfessor(@PathVariable long id,@RequestBody ProfessorDto professorDto){
        return ResponseEntity.ok(professorService.updateProfessor(professorDto,id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteProfessor(@PathVariable  long id){
        professorService.deleteProfessor(id);
        return ResponseEntity.ok("deleted successfully");
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDto>getProfessorById(@PathVariable long id){
        return ResponseEntity.ok(professorService.getProfessorById(id));
    }

}
