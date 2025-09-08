package com.caba.coachsystem.controller;

import com.caba.coachsystem.entity.*;
import com.caba.coachsystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/coach")
public class CoachController {
    
    @Autowired
    private CoachService coachService;
    
    @Autowired
    private PartidoService partidoService;
    
    @Autowired
    private CalificacionService calificacionService;
    
    @Autowired
    private NotificacionService notificacionService;
    
    // ============================================
    // ACTIVIDADES BÁSICAS DEL TALLER (1-6)
    // ============================================
    
    // Actividad 1: Vista inicial
    @GetMapping("/")
    public String inicio() {
        return "coach/inicio";
    }
    
    // Actividad 2: Formulario de creación
    @GetMapping("/crear")
    public String mostrarFormularioCreacion(Model model) {
        model.addAttribute("coach", new Coach());
        return "coach/formulario";
    }
    
    // Actividad 3: Inserción del objeto
    @PostMapping("/guardar")
    public String guardarCoach(@ModelAttribute Coach coach, RedirectAttributes redirectAttributes) {
        
        // Validaciones básicas
        if (coach.getNombre() == null || coach.getNombre().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "El nombre es obligatorio");
            return "redirect:/coach/crear";
        }
        
        if (coach.getApellido() == null || coach.getApellido().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "El apellido es obligatorio");
            return "redirect:/coach/crear";
        }
        
        if (coach.getEmail() == null || coach.getEmail().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "El email es obligatorio");
            return "redirect:/coach/crear";
        }
        
        if (coach.getEquipo() == null || coach.getEquipo().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "El equipo es obligatorio");
            return "redirect:/coach/crear";
        }
        
        try {
            coachService.guardar(coach);
            redirectAttributes.addFlashAttribute("mensaje", "Elemento creado satisfactoriamente");
            return "redirect:/coach/";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear el coach: " + e.getMessage());
            return "redirect:/coach/crear";
        }
    }
    
    // Actividad 4: Listar objetos
    @GetMapping("/listar")
    public String listarCoaches(Model model) {
        List<Coach> coaches = coachService.obtenerTodos();
        model.addAttribute("coaches", coaches);
        return "coach/listar";
    }
    
    // Actividad 5: Ver un objeto
    @GetMapping("/ver/{id}")
    public String verCoach(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Coach> coach = coachService.obtenerPorId(id);
        
        if (coach.isPresent()) {
            model.addAttribute("coach", coach.get());
            return "coach/ver";
        } else {
            redirectAttributes.addFlashAttribute("error", "Coach no encontrado");
            return "redirect:/coach/listar";
        }
    }
    
    // Actividad 6: Borrar objeto
    @PostMapping("/eliminar/{id}")
    public String eliminarCoach(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            if (coachService.existe(id)) {
                coachService.eliminar(id);
                redirectAttributes.addFlashAttribute("mensaje", "Coach eliminado correctamente");
            } else {
                redirectAttributes.addFlashAttribute("error", "Coach no encontrado");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el coach: " + e.getMessage());
        }
        
        return "redirect:/coach/listar";
    }
    
    // ============================================
    // TUS FUNCIONALIDADES ESPECÍFICAS
    // ============================================
    
    // Ver partidos del equipo del coach
    @GetMapping("/partidos/{coachId}")
    public String verPartidosDelCoach(@PathVariable Long coachId, Model model, RedirectAttributes redirectAttributes) {
        Optional<Coach> coachOpt = coachService.obtenerPorId(coachId);
        
        if (!coachOpt.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Coach no encontrado");
            return "redirect:/coach/listar";
        }
        
        Coach coach = coachOpt.get();
        List<Partido> partidos = partidoService.obtenerPartidosPorEquipo(coach.getEquipo());
        
        model.addAttribute("coach", coach);
        model.addAttribute("partidos", partidos);
        return "coach/partidos";
    }
    
    // Formulario para calificar arbitraje
    @GetMapping("/calificar/{coachId}/{partidoId}")
    public String mostrarFormularioCalificacion(@PathVariable Long coachId, 
                                              @PathVariable Long partidoId, 
                                              Model model, 
                                              RedirectAttributes redirectAttributes) {
        
        Optional<Coach> coachOpt = coachService.obtenerPorId(coachId);
        Optional<Partido> partidoOpt = partidoService.obtenerPorId(partidoId);
        
        if (!coachOpt.isPresent() || !partidoOpt.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Coach o partido no encontrado");
            return "redirect:/coach/listar";
        }
        
        Coach coach = coachOpt.get();
        Partido partido = partidoOpt.get();
        
        // Verificar si ya calificó
        if (calificacionService.yaCalificado(coach, partido)) {
            redirectAttributes.addFlashAttribute("error", "Ya has calificado este partido");
            return "redirect:/coach/partidos/" + coachId;
        }
        
        Calificacion calificacion = new Calificacion();
        calificacion.setCoach(coach);
        calificacion.setPartido(partido);
        
        model.addAttribute("calificacion", calificacion);
        model.addAttribute("coach", coach);
        model.addAttribute("partido", partido);
        return "coach/calificar";
    }
    
    // Enviar calificación del arbitraje
    @PostMapping("/enviar-calificacion")
    public String enviarCalificacion(@ModelAttribute Calificacion calificacion, 
                                   RedirectAttributes redirectAttributes) {
        
        // Validaciones
        if (calificacion.getPuntuacion() == null || calificacion.getPuntuacion() < 1 || calificacion.getPuntuacion() > 5) {
            redirectAttributes.addFlashAttribute("error", "La puntuación debe estar entre 1 y 5");
            return "redirect:/coach/calificar/" + calificacion.getCoach().getId() + "/" + calificacion.getPartido().getId();
        }
        
        if (calificacion.getNombreArbitro() == null || calificacion.getNombreArbitro().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "El nombre del árbitro es obligatorio");
            return "redirect:/coach/calificar/" + calificacion.getCoach().getId() + "/" + calificacion.getPartido().getId();
        }
        
        try {
            calificacion.setFechaCalificacion(LocalDateTime.now());
            calificacionService.guardar(calificacion);
            
            redirectAttributes.addFlashAttribute("mensaje", "Calificación enviada exitosamente");
            return "redirect:/coach/partidos/" + calificacion.getCoach().getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al enviar calificación: " + e.getMessage());
            return "redirect:/coach/calificar/" + calificacion.getCoach().getId() + "/" + calificacion.getPartido().getId();
        }
    }
    
    // Ver notificaciones
    @GetMapping("/notificaciones/{coachId}")
    public String verNotificaciones(@PathVariable Long coachId, Model model, RedirectAttributes redirectAttributes) {
        Optional<Coach> coachOpt = coachService.obtenerPorId(coachId);
        
        if (!coachOpt.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Coach no encontrado");
            return "redirect:/coach/listar";
        }
        
        Coach coach = coachOpt.get();
        List<Notificacion> notificaciones = notificacionService.obtenerPorCoach(coach);
        Long noLeidas = notificacionService.contarNoLeidasPorCoach(coach);
        
        model.addAttribute("coach", coach);
        model.addAttribute("notificaciones", notificaciones);
        model.addAttribute("noLeidas", noLeidas);
        return "coach/notificaciones";
    }
    
    // Marcar notificación como leída
    @PostMapping("/marcar-leida/{notificacionId}")
    public String marcarComoLeida(@PathVariable Long notificacionId, 
                                @RequestParam Long coachId,
                                RedirectAttributes redirectAttributes) {
        try {
            notificacionService.marcarComoLeida(notificacionId);
            redirectAttributes.addFlashAttribute("mensaje", "Notificación marcada como leída");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al marcar notificación");
        }
        
        return "redirect:/coach/notificaciones/" + coachId;
    }
    
    // Ver mis calificaciones enviadas
    @GetMapping("/mis-calificaciones/{coachId}")
    public String verMisCalificaciones(@PathVariable Long coachId, Model model, RedirectAttributes redirectAttributes) {
        Optional<Coach> coachOpt = coachService.obtenerPorId(coachId);
        
        if (!coachOpt.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Coach no encontrado");
            return "redirect:/coach/listar";
        }
        
        Coach coach = coachOpt.get();
        List<Calificacion> calificaciones = calificacionService.obtenerPorCoach(coach);
        
        model.addAttribute("coach", coach);
        model.addAttribute("calificaciones", calificaciones);
        return "coach/mis-calificaciones";
    }
    
    // Crear datos de ejemplo (solo para testing)
    @GetMapping("/crear-datos-ejemplo")
    public String crearDatosEjemplo(RedirectAttributes redirectAttributes) {
        try {
            // Crear coach de ejemplo
            Coach coach1 = new Coach("Juan", "Pérez", "juan@email.com", "300-123-4567", "Lakers Medellín", 5);
            coachService.guardar(coach1);
            
            // Crear partidos de ejemplo
            Partido partido1 = new Partido("Lakers Medellín", "Bulls Bogotá", 
                LocalDateTime.of(2025, 1, 15, 19, 0), "Coliseo Mayor", "Liga Nacional");
            partido1.setArbitroPrincipal("Carlos Rodríguez");
            partido1.setEstado("FINALIZADO");
            partidoService.guardar(partido1);
            
            Partido partido2 = new Partido("Lakers Medellín", "Warriors Cali", 
                LocalDateTime.of(2025, 1, 20, 20, 0), "Coliseo del Pueblo", "Copa Regional");
            partido2.setArbitroPrincipal("María García");
            partido2.setEstado("PROGRAMADO");
            partidoService.guardar(partido2);
            
            redirectAttributes.addFlashAttribute("mensaje", "Datos de ejemplo creados correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear datos: " + e.getMessage());
        }
        
        return "redirect:/coach/";
    }
}