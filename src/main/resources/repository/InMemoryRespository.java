package com.uady.sicei.repository;

import com.uady.sicei.model.Alumno;
import com.uady.sicei.model.Profesor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryRepository {

    private final Map<Integer, Alumno> alumnos = new LinkedHashMap<>();
    private final Map<Integer, Profesor> profesores = new LinkedHashMap<>();
    private final AtomicInteger alumnoIdSeq = new AtomicInteger(0);
    private final AtomicInteger profesorIdSeq = new AtomicInteger(0);

    // Alumnos
    public List<Alumno> findAllAlumnos() { return new ArrayList<>(alumnos.values()); }
    public Optional<Alumno> findAlumnoById(Integer id) { return Optional.ofNullable(alumnos.get(id)); }
    public Alumno saveAlumno(Alumno a) {
        if (a.getId() == null) a.setId(alumnoIdSeq.incrementAndGet());
        alumnos.put(a.getId(), a);
        return a;
    }
    public void deleteAlumno(Integer id) { alumnos.remove(id); }

    // Profesores
    public List<Profesor> findAllProfesores() { return new ArrayList<>(profesores.values()); }
    public Optional<Profesor> findProfesorById(Integer id) { return Optional.ofNullable(profesores.get(id)); }
    public Profesor saveProfesor(Profesor p) {
        if (p.getId() == null) p.setId(profesorIdSeq.incrementAndGet());
        profesores.put(p.getId(), p);
        return p;
    }
    public void deleteProfesor(Integer id) { profesores.remove(id); }
}