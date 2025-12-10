package cl.web2.prueba3.prueba3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.web2.prueba3.prueba3.models.Profesor;
import cl.web2.prueba3.prueba3.repository.ProfesorRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ProfesorService {
    
    @Autowired
    private ProfesorRepository profesorRepository;
    
    public Profesor crearProfesor(Profesor profesor) {
        return profesorRepository.save(profesor);
    }
    
    public Optional<Profesor> obtenerProfesor(Long id) {
        return profesorRepository.findById(id);
    }
    
    public List<Profesor> obtenerTodosLosProfesores() {
        return (List<Profesor>) profesorRepository.findAll();
    }
    
    public Profesor actualizarProfesor(Long id, Profesor profesorActualizado) {
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
    
    public boolean eliminarProfesor(Long id) {
        if (profesorRepository.existsById(id)) {
            profesorRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public Profesor obtenerProfesorPorCorreo(String correo) {
        return profesorRepository.findByCorreoElectronico(correo);
    }
}
