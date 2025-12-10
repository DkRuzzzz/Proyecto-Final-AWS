package mx.uady.aws.sice_api.profesores;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/profesores")
public class ProfesorController {

    private final ProfesorRepository repository;

    public ProfesorController(ProfesorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Profesor> getProfesores() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profesor> getProfesor(@PathVariable Integer id) {
        Profesor profesor = repository.findById(id);
        if (profesor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(profesor);
    }

    @PostMapping
    public ResponseEntity<?> createProfesor(@RequestBody Profesor profesor) {
        String error = validateProfesor(profesor);
        if (error != null) {
            return badRequest(error);
        }
        repository.save(profesor);
        return ResponseEntity.status(HttpStatus.CREATED).body(profesor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfesor(@PathVariable Integer id,
                                            @RequestBody Profesor profesor) {

        if (!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        profesor.setId(id);
        String error = validateProfesor(profesor);
        if (error != null) {
            return badRequest(error);
        }

        repository.save(profesor);
        return ResponseEntity.ok(profesor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfesor(@PathVariable Integer id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // ================= VALIDACIÓN SIMPLE =================

    private String validateProfesor(Profesor profesor) {
        if (profesor == null) {
            return "Profesor requerido";
        }
        if (profesor.getId() == null || profesor.getId() <= 0) {
            return "Id inválido";
        }
        if (isBlank(profesor.getNombres())) {
            return "Nombres inválidos";
        }
        if (isBlank(profesor.getApellidos())) {
            return "Apellidos inválidos";
        }
        if (profesor.getNumeroEmpleado() == null || profesor.getNumeroEmpleado() <= 0) {
            return "Número de empleado inválido";
        }
        if (profesor.getHorasClase() == null || profesor.getHorasClase() < 0) {
            return "Horas de clase inválidas";
        }
        return null;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private ResponseEntity<Map<String, String>> badRequest(String msg) {
        Map<String, String> body = new HashMap<>();
        body.put("error", msg);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
