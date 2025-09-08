package com.caba.coachsystem.repository;

import com.caba.coachsystem.entidad.Notificacion;
import com.caba.coachsystem.entidad.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    
    List<Notificacion> findByCoachOrderByFechaCreacionDesc(Coach coach);
    
    @Query("SELECT COUNT(n) FROM Notificacion n WHERE n.coach = :coach AND n.leida = false")
    Long countNotificacionesNoLeidasByCoach(@Param("coach") Coach coach);
}