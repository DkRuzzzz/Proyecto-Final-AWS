package mx.uady.aws.sice_api.profesores;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ProfesorRepository {

    private final Map<Integer, Profesor> data = new ConcurrentHashMap<>();

    public List<Profesor> findAll() {
        return new ArrayList<>(data.values());
    }

    public Profesor findById(Integer id) {
        return data.get(id);
    }

    public Profesor save(Profesor profesor) {
        data.put(profesor.getId(), profesor);
        return profesor;
    }

    public boolean existsById(Integer id) {
        return data.containsKey(id);
    }

    public void deleteById(Integer id) {
        data.remove(id);
    }
}
