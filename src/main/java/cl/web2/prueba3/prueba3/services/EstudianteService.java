package cl.web2.prueba3.prueba3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import cl.web2.prueba3.prueba3.models.Estudiante;
import cl.web2.prueba3.prueba3.repository.EstudianteRepository;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class EstudianteService {
    
    @Autowired
    private EstudianteRepository estudianteRepository;
    
    @Transactional
    public Estudiante crearEstudiante(@NonNull Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }
    
    public Optional<Estudiante> obtenerEstudiante(@NonNull Long id) {
        return estudianteRepository.findById(id);
    }
    
    public List<Estudiante> obtenerTodosLosEstudiantes() {
        return (List<Estudiante>) estudianteRepository.findAll();
    }
    
    @Transactional
    public Estudiante actualizarEstudiante(@NonNull Long id, @NonNull Estudiante estudianteActualizado) {
        Optional<Estudiante> estudianteExistente = estudianteRepository.findById(id);
        if (estudianteExistente.isPresent()) {
            Estudiante estudiante = estudianteExistente.get();
            estudiante.setNombre(estudianteActualizado.getNombre());
            estudiante.setApellido(estudianteActualizado.getApellido());
            estudiante.setEmail(estudianteActualizado.getEmail());
            estudiante.setCarrera(estudianteActualizado.getCarrera());
            return estudianteRepository.save(estudiante);
        }
        return null;
    }
    
    @Transactional
    public boolean eliminarEstudiante(@NonNull Long id) {
        if (estudianteRepository.existsById(id)) {
            estudianteRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    @Nullable
    public Estudiante obtenerEstudiantePorEmail(@NonNull String email) {
        return estudianteRepository.findByEmail(email).orElse(null);
    }
    
    public Estudiante obtenerPorEmail(String email) {
        return estudianteRepository.findByEmail(email).orElse(null);
    }
    
    public List<Estudiante> obtenerEstudiantesPorCarrera(Long carreraId) {
        return estudianteRepository.findByCarreraId(carreraId);
    }
}
