package com.taller01.coach.repository;

import com.taller01.coach.model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Long> {
    
    // Buscar coach por cedula
    Optional<Coach> findByCedula(String cedula);
    
    // Verificar si existe un coach con la cedula que ingresaron 
    boolean existsByCedula(String cedula);
}