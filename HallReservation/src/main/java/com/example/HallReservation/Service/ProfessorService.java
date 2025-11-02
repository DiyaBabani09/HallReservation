package com.example.HallReservation.Service;

import com.example.HallReservation.Dto.ProfessorDto;
import com.example.HallReservation.Entity.Professor;
import com.example.HallReservation.Exception.ProfessorHandleException;
import com.example.HallReservation.Respository.ProfessorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {
    private final ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public ProfessorDto saveProfessor(ProfessorDto professorDto){
        if(professorDto!=null){
           Optional<Professor> professor= professorRepository.findByEmail(professorDto.getEmail());
           if(professor.isPresent()){
               throw new RuntimeException("email is registered");
           }
        }
        Professor p =new Professor();
        p.setId(professorDto.getId());
        p.setEmail(professorDto.getEmail());
        p.setName(professorDto.getName());

     Professor p1=professorRepository.save(p);
         ProfessorDto professorDto1=new ProfessorDto();
         professorDto1.setId(p1.getId());
         professorDto1.setName(p1.getName());
         professorDto1.setEmail(p1.getEmail());
         return professorDto1;
    }
    public Page<ProfessorDto > GetProfessor(int pageNo,int pageSize){
        Pageable pageable= PageRequest.of(pageNo,pageSize);
        Page<Professor>p=professorRepository.findAll(pageable);
        List<ProfessorDto>professorDtoList=new ArrayList<>();
        for(Professor page:p){
            ProfessorDto professorDto=new ProfessorDto();
            professorDto.setId(page.getId());
            professorDto.setName(page.getName());
            professorDto.setEmail(page.getEmail());
            professorDtoList.add(professorDto);
        }
return new PageImpl<>(professorDtoList,pageable,p.getTotalElements());
    }
    public ProfessorDto updateProfessor(ProfessorDto professorDto,long id){
      Professor p=  professorRepository.findById(id).
                orElseThrow(()->new ProfessorHandleException("Id does not exist"));

        p.setEmail(professorDto.getEmail());
        p.setName(professorDto.getName());
        Professor p1=professorRepository.save(p);
        ProfessorDto professorDto1=new ProfessorDto();
        professorDto1.setId(p1.getId());
        professorDto1.setName(p1.getName());
        professorDto1.setEmail(p1.getEmail());
        return professorDto1;
    }
    public void deleteProfessor(long id){
      professorRepository.findById(id).orElseThrow(()->new ProfessorHandleException("Id not found"));
       professorRepository.deleteById(id);
    }
    public ProfessorDto getProfessorById(long id){
        Professor p=  professorRepository.findById(id).
                orElseThrow(()->new ProfessorHandleException("Id does not exist"));

        ProfessorDto professorDto=new ProfessorDto();
professorDto.setId(p.getId());
        professorDto.setName(p.getName());
        professorDto.setEmail(p.getEmail());
        return professorDto;


    }
}

