package cl.web2.prueba3.prueba3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.web2.prueba3.prueba3.models.Practica;
import cl.web2.prueba3.prueba3.repository.PracticaRepository;
import java.util.List;
import java.util.Optional;

@Service
public class PracticaService {
    
    @Autowired
    private PracticaRepository practicaRepository;
    
    public Practica crearPractica(Practica practica) {
        return practicaRepository.save(practica);
    }
    
    public Optional<Practica> obtenerPractica(Long id) {
        return practicaRepository.findById(id);
    }
    
    public List<Practica> obtenerTodasLasPracticas() {
        return (List<Practica>) practicaRepository.findAll();
    }
    
    public Practica actualizarPractica(Long id, Practica practicaActualizada) {
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
    
    public boolean eliminarPractica(Long id) {
        if (practicaRepository.existsById(id)) {
            practicaRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public List<Practica> obtenerPracticasPorEstudiante(Long estudianteId) {
        return practicaRepository.findByEstudianteId(estudianteId);
    }
    
    public List<Practica> obtenerPracticasPorEmpresa(Long empresaId) {
        return practicaRepository.findByEmpresaId(empresaId);
    }
    
    public List<Practica> obtenerPracticasPorProfesor(Long profesorId) {
        return practicaRepository.findByProfesorId(profesorId);
    }
}
