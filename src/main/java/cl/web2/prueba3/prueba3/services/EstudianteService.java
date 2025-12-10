package cl.web2.prueba3.prueba3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.web2.prueba3.prueba3.models.Estudiante;
import cl.web2.prueba3.prueba3.repository.EstudianteRepository;
import java.util.List;
import java.util.Optional;

@Service
public class EstudianteService {
    
    @Autowired
    private EstudianteRepository estudianteRepository;
    
    public Estudiante crearEstudiante(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }
    
    public Optional<Estudiante> obtenerEstudiante(Long id) {
        return estudianteRepository.findById(id);
    }
    
    public List<Estudiante> obtenerTodosLosEstudiantes() {
        return (List<Estudiante>) estudianteRepository.findAll();
    }
    
    public Estudiante actualizarEstudiante(Long id, Estudiante estudianteActualizado) {
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
    
    public boolean eliminarEstudiante(Long id) {
        if (estudianteRepository.existsById(id)) {
            estudianteRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public Estudiante obtenerEstudiantePorEmail(String email) {
        return estudianteRepository.findByEmail(email);
    }
    
    public List<Estudiante> obtenerEstudiantesPorCarrera(Long carreraId) {
        return estudianteRepository.findByCarreraId(carreraId);
    }
}
