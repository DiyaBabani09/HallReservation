package com.example.HallReservation.Respository;

import com.example.HallReservation.Entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
 Optional<Professor> findByEmail(String email);
 Optional<Professor> findByNameAndEmail(String name, String email);
}
