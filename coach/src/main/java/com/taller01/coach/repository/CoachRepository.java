package com.taller01.coach.repository;

import com.taller01.coach.model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Long> {
    
    // Buscar coach por cédula
    Optional<Coach> findByCedula(String cedula);
    
    // Verificar si existe un coach con la cédula dada
    boolean existsByCedula(String cedula);
}