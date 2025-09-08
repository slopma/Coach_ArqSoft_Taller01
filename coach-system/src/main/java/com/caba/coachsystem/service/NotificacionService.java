package com.caba.coachsystem.service;

import com.caba.coachsystem.entidad.Coach;
import com.caba.coachsystem.entidad.Notificacion;
import com.caba.coachsystem.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificacionService {
    
    @Autowired
    private NotificacionRepository notificacionRepository;
    
    public List<Notificacion> obtenerPorCoach(Coach coach) {
        return notificacionRepository.findByCoachOrderByFechaCreacionDesc(coach);
    }
    
    public Long contarNoLeidasPorCoach(Coach coach) {
        return notificacionRepository.countNotificacionesNoLeidasByCoach(coach);
    }
    
    public Notificacion crearNotificacion(Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }
    
    public void marcarComoLeida(Long notificacionId) {
        notificacionRepository.findById(notificacionId).ifPresent(n -> {
            n.setLeida(true);
            notificacionRepository.save(n);
        });
    }
}