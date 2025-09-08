package com.caba.coachsystem.service;

import com.caba.coachsystem.entidad.Coach;
import com.caba.coachsystem.repository.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CoachService {
    
    @Autowired
    private CoachRepository coachRepository;
    
    public List<Coach> obtenerTodos() {
        return coachRepository.findAll();
    }
    
    public Optional<Coach> obtenerPorId(Long id) {
        return coachRepository.findById(id);
    }
    
    public Coach guardar(Coach coach) {
        return coachRepository.save(coach);
    }
    
    public void eliminar(Long id) {
        coachRepository.deleteById(id);
    }
    
    public boolean existe(Long id) {
        return coachRepository.existsById(id);
    }
}