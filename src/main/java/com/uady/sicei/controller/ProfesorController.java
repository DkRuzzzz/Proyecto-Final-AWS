package com.uady.sicei.controller;

import com.uady.sicei.exception.NotFoundException;
import com.uady.sicei.model.Profesor;
import com.uady.sicei.repository.InMemoryRepository;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profesores")
public class ProfesorController {

    private final InMemoryRepository repo;

    public ProfesorController(InMemoryRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public ResponseEntity<List<Profesor>> getAll() {
        return ResponseEntity.ok(repo.findAllProfesores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profesor> getById(@PathVariable Integer id) {
        return repo.findProfesorById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException("Profesor no encontrado"));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Profesor profesor, BindingResult br) {
        if (br.hasErrors()) return ResponseEntity.badRequest().body(br.getAllErrors());
        Profesor saved = repo.saveProfesor(profesor);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody Profesor profesor, BindingResult br) {
        if (br.hasErrors()) return ResponseEntity.badRequest().body(br.getAllErrors());
        return repo.findProfesorById(id).map(existing -> {
            profesor.setId(id);
            repo.saveProfesor(profesor);
            return ResponseEntity.ok(profesor);
        }).orElseThrow(() -> new NotFoundException("Profesor no encontrado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (repo.findProfesorById(id).isEmpty()) throw new NotFoundException("Profesor no encontrado");
        repo.deleteProfesor(id);
        return ResponseEntity.ok().build();
    }
}