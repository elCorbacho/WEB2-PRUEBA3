package cl.web2.prueba3.prueba3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.lang.NonNull;
import cl.web2.prueba3.prueba3.models.Practica;
import cl.web2.prueba3.prueba3.repository.PracticaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PracticaService {
    
    @Autowired
    private PracticaRepository practicaRepository;
    
    /**
     * Verificar si el estudiante tiene una práctica en curso
     */
    public boolean tienePracticaEnCurso(Long estudianteId) {
        LocalDate hoy = LocalDate.now();
        List<Practica> practicas = practicaRepository.findByEstudianteId(estudianteId);
        
        return practicas.stream().anyMatch(p -> 
            !p.getFechaInicio().isAfter(hoy) && !p.getFechaFin().isBefore(hoy)
        );
    }
    
    /**
     * Obtener práctica en curso del estudiante
     */
    public Optional<Practica> obtenerPracticaEnCurso(Long estudianteId) {
        LocalDate hoy = LocalDate.now();
        List<Practica> practicas = practicaRepository.findByEstudianteId(estudianteId);
        
        return practicas.stream().filter(p -> 
            !p.getFechaInicio().isAfter(hoy) && !p.getFechaFin().isBefore(hoy)
        ).findFirst();
    }
    
    @Transactional
    public Practica crearPractica(@NonNull Practica practica) {
        return practicaRepository.save(practica);
    }
    
    public Optional<Practica> obtenerPractica(@NonNull Long id) {
        return practicaRepository.findById(id);
    }
    
    public List<Practica> obtenerTodasLasPracticas() {
        return (List<Practica>) practicaRepository.findAll();
    }
    
    public List<Practica> obtenerPracticasPorEstudiante(@NonNull Long estudianteId) {
        return practicaRepository.findByEstudianteId(estudianteId);
    }
    
    @Transactional
    public Practica actualizarPractica(@NonNull Long id, @NonNull Practica practicaActualizada) {
        Optional<Practica> practicaExistente = practicaRepository.findById(id);
        if (practicaExistente.isPresent()) {
            Practica practica = practicaExistente.get();
            practica.setEstudiante(practicaActualizada.getEstudiante());
            practica.setEmpresa(practicaActualizada.getEmpresa());
            practica.setFechaInicio(practicaActualizada.getFechaInicio());
            practica.setFechaFin(practicaActualizada.getFechaFin());
            practica.setActividades(practicaActualizada.getActividades());
            practica.setProfesor(practicaActualizada.getProfesor());
            return practicaRepository.save(practica);
        }
        return null;
    }
    
    @Transactional
    public boolean eliminarPractica(@NonNull Long id) {
        if (practicaRepository.existsById(id)) {
            practicaRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public List<Practica> obtenerPracticasPorEmpresa(Long empresaId) {
        return practicaRepository.findByEmpresaId(empresaId);
    }
    
    public List<Practica> obtenerPracticasPorProfesor(Long profesorId) {
        return practicaRepository.findByProfesorId(profesorId);
    }
}
