package com.taller01.coach.controller;

import com.taller01.coach.model.Coach;
import com.taller01.coach.service.CoachService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.Optional;

@Controller
public class CoachController {

    private final CoachService coachService;

    public CoachController(CoachService coachService) {
        this.coachService = coachService;
    }

    // Ruta raíz - redirige a /home
    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }

    // Actividad 1: Vista inicial con 2 botones/enlaces
    @GetMapping("/home")
    public String home() {
        return "home/index";
    }

    // Actividad 2: Formulario de creación
    @GetMapping("/coach/crear")
    public String mostrarFormulario(Model model) {
        model.addAttribute("coach", new Coach());
        return "coach/crear";
    }

    // Actividad 3: Inserción del objeto (procesar formulario)
    @PostMapping("/coach/guardar")
    public String guardarCoach(@Valid @ModelAttribute("coach") Coach coach,
                              BindingResult result, 
                              Model model, 
                              RedirectAttributes redirectAttributes) {
        
        // Validar errores de formato
        if (result.hasErrors()) {
            return "coach/crear";
        }

        try {
            // Validar cédula unica
            Optional<Coach> existingCoach = coachService.findByCedula(coach.getCedula());
            if (existingCoach.isPresent()) {
                model.addAttribute("errorMessage", "Ya existe un coach con esta cédula");
                return "coach/crear";
            }

            // Guardar en bd
            coachService.save(coach);
            
            // Mensaje de éxito como pide el taller jeje
            redirectAttributes.addFlashAttribute("successMessage", "Elemento creado satisfactoriamente");
            return "redirect:/coach/listar";
            
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al guardar el coach: " + e.getMessage());
            return "coach/crear";
        }
    }

    // Actividad 4: Listar objetos (solo ID y nombre)
    @GetMapping("/coach/listar")
    public String listarCoaches(Model model) {
        model.addAttribute("coaches", coachService.findAll());
        return "coach/listar";
    }

    // Actividad 5: Ver un objeto completo
    @GetMapping("/coach/ver/{id}")
    public String verCoach(@PathVariable Long id, Model model) {
        Optional<Coach> coach = coachService.findById(id);
        
        if (coach.isPresent()) {
            model.addAttribute("coach", coach.get());
            return "coach/show";
        } else {
            return "redirect:/coach/listar";
        }
    }

    // Actividad 6: Borrar objeto
    @PostMapping("/coach/eliminar/{id}")
    public String eliminarCoach(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            // Obtener nombre antes de eliminar para el mensaje
            Optional<Coach> coach = coachService.findById(id);
            String nombreCoach = coach.map(Coach::getNombre).orElse("Coach");
            
            // Eliminar
            coachService.deleteById(id);
            
            redirectAttributes.addFlashAttribute("successMessage", 
                "Coach '" + nombreCoach + "' eliminado exitosamente");
                
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Error al eliminar el coach: " + e.getMessage());
        }
        
        return "redirect:/coach/listar";
    }
}