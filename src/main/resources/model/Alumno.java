package com.uady.sicei.model;

import jakarta.validation.constraints.*;

public class Alumno {
    private Integer id;

    @NotBlank(message = "nombres es obligatorio")
    private String nombres;

    @NotBlank(message = "apellidos es obligatorio")
    private String apellidos;

    @NotBlank(message = "matricula es obligatoria")
    private String matricula;

    @NotNull(message = "promedio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true)
    @DecimalMax(value = "10.0", inclusive = true)
    private Double promedio;

    public Alumno() {}

    public Alumno(Integer id, String nombres, String apellidos, String matricula, Double promedio) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.matricula = matricula;
        this.promedio = promedio;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public Double getPromedio() { return promedio; }
    public void setPromedio(Double promedio) { this.promedio = promedio; }
}