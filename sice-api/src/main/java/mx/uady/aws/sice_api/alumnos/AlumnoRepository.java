package mx.uady.aws.sice_api.alumnos;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AlumnoRepository {

    private final Map<Integer, Alumno> data = new ConcurrentHashMap<>();

    public List<Alumno> findAll() {
        return new ArrayList<>(data.values());
    }

    public Alumno findById(Integer id) {
        return data.get(id);
    }

    public Alumno save(Alumno alumno) {
        data.put(alumno.getId(), alumno);
        return alumno;
    }

    public boolean existsById(Integer id) {
        return data.containsKey(id);
    }

    public void deleteById(Integer id) {
        data.remove(id);
    }
}
