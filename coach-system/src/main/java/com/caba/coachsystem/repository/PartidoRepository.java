package com.caba.coachsystem.repository;

import com.caba.coachsystem.entidad.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PartidoRepository extends JpaRepository<Partido, Long> {
    
    @Query("SELECT p FROM Partido p WHERE p.equipoLocal = :equipo OR p.equipoVisitante = :equipo")
    List<Partido> findByEquipo(@Param("equipo") String equipo);
    
    List<Partido> findByTorneo(String torneo);
}