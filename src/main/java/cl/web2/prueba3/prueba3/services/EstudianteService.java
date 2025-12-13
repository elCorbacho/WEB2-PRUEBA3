package cl.web2.prueba3.prueba3.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.lang.NonNull;
import cl.web2.prueba3.prueba3.models.Estudiante;
import cl.web2.prueba3.prueba3.repository.EstudianteRepository;
import cl.web2.prueba3.prueba3.exceptions.ResourceNotFoundException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EstudianteService {
    
    private final EstudianteRepository estudianteRepository;
    
    @Transactional
    public Estudiante crearEstudiante(@NonNull Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }
    
    public Estudiante obtenerEstudiante(@NonNull Long id) {
        return estudianteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Estudiante", "id", id));
    }
    
    public List<Estudiante> obtenerTodosLosEstudiantes() {
        return estudianteRepository.findAll();
    }
    
    @Transactional
    public Estudiante actualizarEstudiante(@NonNull Long id, @NonNull Estudiante estudianteActualizado) {
        Estudiante estudiante = obtenerEstudiante(id);
        estudiante.setNombre(estudianteActualizado.getNombre());
        estudiante.setApellido(estudianteActualizado.getApellido());
        estudiante.setEmail(estudianteActualizado.getEmail());
        estudiante.setCarrera(estudianteActualizado.getCarrera());
        return estudianteRepository.save(estudiante);
    }
    
    @Transactional
    public void eliminarEstudiante(@NonNull Long id) {
        if (!estudianteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Estudiante", "id", id);
        }
        estudianteRepository.deleteById(id);
    }
    
    public Estudiante obtenerPorEmail(@NonNull String email) {
        return estudianteRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Estudiante", "email", email));
    }
    
    public List<Estudiante> obtenerEstudiantesPorCarrera(@NonNull Long carreraId) {
        return estudianteRepository.findByCarreraId(carreraId);
    }
}
