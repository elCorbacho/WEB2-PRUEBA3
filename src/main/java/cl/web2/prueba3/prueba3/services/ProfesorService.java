package cl.web2.prueba3.prueba3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import cl.web2.prueba3.prueba3.models.Profesor;
import cl.web2.prueba3.prueba3.repository.ProfesorRepository;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProfesorService {
    
    @Autowired
    private ProfesorRepository profesorRepository;
    
    @Transactional
    public Profesor crearProfesor(@NonNull Profesor profesor) {
        return profesorRepository.save(profesor);
    }
    
    public Optional<Profesor> obtenerProfesor(@NonNull Long id) {
        return profesorRepository.findById(id);
    }
    
    public List<Profesor> obtenerTodosLosProfesores() {
        return (List<Profesor>) profesorRepository.findAll();
    }
    
    @Transactional
    public Profesor actualizarProfesor(@NonNull Long id, @NonNull Profesor profesorActualizado) {
        Optional<Profesor> profesorExistente = profesorRepository.findById(id);
        if (profesorExistente.isPresent()) {
            Profesor profesor = profesorExistente.get();
            profesor.setNombres(profesorActualizado.getNombres());
            profesor.setApellidos(profesorActualizado.getApellidos());
            profesor.setCorreoElectronico(profesorActualizado.getCorreoElectronico());
            return profesorRepository.save(profesor);
        }
        return null;
    }
    
    @Transactional
    public boolean eliminarProfesor(@NonNull Long id) {
        if (profesorRepository.existsById(id)) {
            profesorRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    @Nullable
    public Profesor obtenerProfesorPorCorreo(@NonNull String correo) {
        return profesorRepository.findByCorreoElectronico(correo);
    }
    
    public Profesor obtenerPorEmail(String email) {
        return profesorRepository.findByEmail(email).orElse(null);
    }
}
