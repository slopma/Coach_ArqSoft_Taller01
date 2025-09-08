package com.caba.coachsystem.service;

import com.caba.coachsystem.entidad.Partido;
import com.caba.coachsystem.repository.PartidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PartidoService {
    
    @Autowired
    private PartidoRepository partidoRepository;
    
    public List<Partido> obtenerTodos() {
        return partidoRepository.findAll();
    }
    
    public Optional<Partido> obtenerPorId(Long id) {
        return partidoRepository.findById(id);
    }
    
    public List<Partido> obtenerPartidosPorEquipo(String equipo) {
        return partidoRepository.findByEquipo(equipo);
    }
    
    public Partido guardar(Partido partido) {
        return partidoRepository.save(partido);
    }
}
