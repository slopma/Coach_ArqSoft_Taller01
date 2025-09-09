package com.taller01.coach.service;

import com.taller01.coach.model.Coach;
import com.taller01.coach.repository.CoachRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CoachService {

    private final CoachRepository coachRepository;

    public CoachService(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }

    // Crear o actualizar un coach
    public Coach save(Coach coach) {
        return coachRepository.save(coach);
    }

    // Buscar todos los coaches
    public List<Coach> findAll() {
        return coachRepository.findAll();
    }

    // Buscar coach por ID
    public Optional<Coach> findById(Long id) {
        return coachRepository.findById(id);
    }

    // Buscar coach por cedula (para validar duplicados)
    public Optional<Coach> findByCedula(String cedula) {
        return coachRepository.findByCedula(cedula);
    }

    // Verificar si existe un coach con la cedula dada
    public boolean existsByCedula(String cedula) {
        return coachRepository.existsByCedula(cedula);
    }

    // Eliminar coach por ID
    public void deleteById(Long id) {
        coachRepository.deleteById(id);
    }

    // Verificar si existe un coach por ID
    public boolean existsById(Long id) {
        return coachRepository.existsById(id);
    }
}