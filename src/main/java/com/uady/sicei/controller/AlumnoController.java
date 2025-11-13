package com.uady.sicei.controller;

import com.uady.sicei.exception.NotFoundException;
import com.uady.sicei.model.Alumno;
import com.uady.sicei.repository.InMemoryRepository;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

    private final InMemoryRepository repo;

    public AlumnoController(InMemoryRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public ResponseEntity<List<Alumno>> getAll() {
        return ResponseEntity.ok(repo.findAllAlumnos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alumno> getById(@PathVariable Integer id) {
        return repo.findAlumnoById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException("Alumno no encontrado"));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Alumno alumno, BindingResult br) {
        if (br.hasErrors()) return ResponseEntity.badRequest().body(br.getAllErrors());
        Alumno saved = repo.saveAlumno(alumno);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody Alumno alumno, BindingResult br) {
        if (br.hasErrors()) return ResponseEntity.badRequest().body(br.getAllErrors());
        return repo.findAlumnoById(id).map(existing -> {
            alumno.setId(id);
            repo.saveAlumno(alumno);
            return ResponseEntity.ok(alumno);
        }).orElseThrow(() -> new NotFoundException("Alumno no encontrado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (repo.findAlumnoById(id).isEmpty()) throw new NotFoundException("Alumno no encontrado");
        repo.deleteAlumno(id);
        return ResponseEntity.ok().build();
    }
}