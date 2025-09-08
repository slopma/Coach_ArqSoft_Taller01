package com.caba.coachsystem.repository;

import com.caba.coachsystem.entidad.Calificacion;
import com.caba.coachsystem.entidad.Coach;
import com.caba.coachsystem.entidad.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    
    List<Calificacion> findByCoach(Coach coach);
    Optional<Calificacion> findByCoachAndPartido(Coach coach, Partido partido);
}