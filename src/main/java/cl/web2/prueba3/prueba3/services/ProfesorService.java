package cl.web2.prueba3.prueba3.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.lang.NonNull;
import cl.web2.prueba3.prueba3.models.Profesor;
import cl.web2.prueba3.prueba3.repository.ProfesorRepository;
import cl.web2.prueba3.prueba3.exceptions.ResourceNotFoundException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfesorService {
    
    private final ProfesorRepository profesorRepository;
    
    @Transactional
    public Profesor crearProfesor(@NonNull Profesor profesor) {
        return profesorRepository.save(profesor);
    }
    
    public Profesor obtenerProfesor(@NonNull Long id) {
        return profesorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Profesor", "id", id));
    }
    
    public List<Profesor> obtenerTodosLosProfesores() {
        return profesorRepository.findAll();
    }
    
    @Transactional
    public Profesor actualizarProfesor(@NonNull Long id, @NonNull Profesor profesorActualizado) {
        Profesor profesor = obtenerProfesor(id);
        profesor.setNombres(profesorActualizado.getNombres());
        profesor.setApellidos(profesorActualizado.getApellidos());
        profesor.setCorreoElectronico(profesorActualizado.getCorreoElectronico());
        return profesorRepository.save(profesor);
    }
    
    @Transactional
    public void eliminarProfesor(@NonNull Long id) {
        if (!profesorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Profesor", "id", id);
        }
        profesorRepository.deleteById(id);
    }
    
    public Profesor obtenerProfesorPorCorreo(@NonNull String correo) {
        Profesor profesor = profesorRepository.findByCorreoElectronico(correo);
        if (profesor == null) {
            throw new ResourceNotFoundException("Profesor", "correo", correo);
        }
        return profesor;
    }
    
    public Profesor obtenerPorEmail(@NonNull String email) {
        return profesorRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Profesor", "email", email));
    }
}
