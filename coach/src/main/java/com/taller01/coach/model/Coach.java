package com.taller01.coach.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

@Entity
@Table(name = "coaches")
public class Coach {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @NotBlank(message = "La cédula no puede estar vacía")
    @Column(nullable = false, length = 20, unique = true)
    private String cedula;
    
    @NotBlank(message = "El teléfono no puede estar vacío")
    @Column(nullable = false, length = 20)
    private String telefono;
    
    @NotNull(message = "La edad es obligatoria")
    @Min(value = 18, message = "La edad mínima es 18 años")
    @Max(value = 80, message = "La edad máxima es 80 años")
    @Column(nullable = false)
    private Integer edad;
    
    @NotNull(message = "Los años de experiencia son obligatorios")
    @Min(value = 0, message = "Los años de experiencia no pueden ser negativos")
    @Max(value = 50, message = "Los años de experiencia máximos son 50")
    @Column(nullable = false)
    private Integer anosExperiencia;
    
    @NotBlank(message = "El nivel de certificación no puede estar vacío")
    @Column(nullable = false, length = 50)
    private String nivelCertificacion;
    
    // Constructores
    public Coach() {}
    
    public Coach(String nombre, String cedula, String telefono, Integer edad, Integer anosExperiencia, String nivelCertificacion) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.telefono = telefono;
        this.edad = edad;
        this.anosExperiencia = anosExperiencia;
        this.nivelCertificacion = nivelCertificacion;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getCedula() {
        return cedula;
    }
    
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public Integer getEdad() {
        return edad;
    }
    
    public void setEdad(Integer edad) {
        this.edad = edad;
    }
    
    public Integer getAnosExperiencia() {
        return anosExperiencia;
    }
    
    public void setAnosExperiencia(Integer anosExperiencia) {
        this.anosExperiencia = anosExperiencia;
    }
    
    public String getNivelCertificacion() {
        return nivelCertificacion;
    }
    
    public void setNivelCertificacion(String nivelCertificacion) {
        this.nivelCertificacion = nivelCertificacion;
    }
    
    @Override
    public String toString() {
        return "Coach{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", cedula='" + cedula + '\'' +
                ", telefono='" + telefono + '\'' +
                ", edad=" + edad +
                ", anosExperiencia=" + anosExperiencia +
                ", nivelCertificacion='" + nivelCertificacion + '\'' +
                '}';
    }
}