package com.caba.coachsystem.service;

import com.caba.coachsystem.entidad.Calificacion;
import com.caba.coachsystem.repository.CalificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CalificacionService {
    
    @Autowired
    private CalificacionRepository calificacionRepository;
    
    @Autowired
    private NotificacionService notificacionService;
    
    public List<Calificacion> obtenerPorCoach(Coach coach) {
        return calificacionRepository.findByCoach(coach);
    }
    
    public Optional<Calificacion> obtenerPorCoachYPartido(Coach coach, Partido partido) {
        return calificacionRepository.findByCoachAndPartido(coach, partido);
    }
    
    public Calificacion guardar(Calificacion calificacion) {
        Calificacion nuevaCalificacion = calificacionRepository.save(calificacion);
        
        // Crear notificación automática
        String mensaje = "Has calificado al árbitro " + calificacion.getNombreArbitro() + 
                        " con " + calificacion.getPuntuacion() + " estrellas";
            
        Notificacion notificacion = new Notificacion(
            calificacion.getCoach(),
            "Calificación Enviada",
            mensaje,
            "CALIFICACION_ENVIADA"
        );
        
        notificacionService.crearNotificacion(notificacion);
        return nuevaCalificacion;
    }
    
    public boolean yaCalificado(Coach coach, Partido partido) {
        return calificacionRepository.findByCoachAndPartido(coach, partido).isPresent();
    }
}