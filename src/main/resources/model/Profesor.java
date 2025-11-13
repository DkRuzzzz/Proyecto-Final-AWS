package com.uady.sicei.model;

import jakarta.validation.constraints.*;

public class Profesor {
    private Integer id;

    @NotBlank(message = "numeroEmpleado es obligatorio")
    private String numeroEmpleado;

    @NotBlank(message = "nombres es obligatorio")
    private String nombres;

    @NotBlank(message = "apellidos es obligatorio")
    private String apellidos;

    @NotNull(message = "horasClase es obligatorio")
    @Min(value = 0)
    private Integer horasClase;

    public Profesor() {}

    public Profesor(Integer id, String numeroEmpleado, String nombres, String apellidos, Integer horasClase) {
        this.id = id;
        this.numeroEmpleado = numeroEmpleado;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.horasClase = horasClase;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNumeroEmpleado() { return numeroEmpleado; }
    public void setNumeroEmpleado(String numeroEmpleado) { this.numeroEmpleado = numeroEmpleado; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public Integer getHorasClase() { return horasClase; }
    public void setHorasClase(Integer horasClase) { this.horasClase = horasClase; }
}