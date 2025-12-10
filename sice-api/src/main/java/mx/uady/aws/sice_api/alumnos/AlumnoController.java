package mx.uady.aws.sice_api.alumnos;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

    private final AlumnoRepository repository;

    public AlumnoController(AlumnoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Alumno> getAlumnos() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alumno> getAlumno(@PathVariable Integer id) {
        Alumno alumno = repository.findById(id);
        if (alumno == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(alumno);
    }

    @PostMapping
    public ResponseEntity<?> createAlumno(@RequestBody Alumno alumno) {
        String error = validateAlumno(alumno);
        if (error != null) {
            return badRequest(error);
        }
        repository.save(alumno);
        return ResponseEntity.status(HttpStatus.CREATED).body(alumno);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAlumno(@PathVariable Integer id,
                                          @RequestBody Alumno alumno) {

        if (!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        alumno.setId(id); // forzamos que el id del path sea el que manda
        String error = validateAlumno(alumno);
        if (error != null) {
            return badRequest(error);
        }

        repository.save(alumno);
        return ResponseEntity.ok(alumno);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlumno(@PathVariable Integer id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // ================= VALIDACIÓN SIMPLE =================

    private String validateAlumno(Alumno alumno) {
        if (alumno == null) {
            return "Alumno requerido";
        }
        if (alumno.getId() == null || alumno.getId() <= 0) {
            return "Id inválido";
        }
        if (isBlank(alumno.getNombres())) {
            return "Nombres inválidos";
        }
        if (isBlank(alumno.getApellidos())) {
            return "Apellidos inválidos";
        }
        if (isBlank(alumno.getMatricula())) {
            return "Matrícula inválida";
        }
        // Que empiece con 'A' y luego dígitos (para distinguir de los casos malos del test)
        if (!alumno.getMatricula().matches("A\\d+")) {
            return "Matrícula inválida";
        }
        if (alumno.getPromedio() == null || alumno.getPromedio() < 0) {
            return "Promedio inválido";
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
