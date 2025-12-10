package cl.web2.prueba3.prueba3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.web2.prueba3.prueba3.models.Carrera;
import cl.web2.prueba3.prueba3.repository.CarreraRepository;
import java.util.List;
import java.util.Optional;

@Service
public class CarreraService {
    
    @Autowired
    private CarreraRepository carreraRepository;
    
    public Carrera crearCarrera(Carrera carrera) {
        return carreraRepository.save(carrera);
    }
    
    public Optional<Carrera> obtenerCarrera(Long id) {
        return carreraRepository.findById(id);
    }
    
    public List<Carrera> obtenerTodasLasCarreras() {
        return (List<Carrera>) carreraRepository.findAll();
    }
    
    public Carrera actualizarCarrera(Long id, Carrera carreraActualizada) {
        Optional<Carrera> carreraExistente = carreraRepository.findById(id);
        if (carreraExistente.isPresent()) {
            Carrera carrera = carreraExistente.get();
            carrera.setNombreCarrera(carreraActualizada.getNombreCarrera());
            return carreraRepository.save(carrera);
        }
        return null;
    }
    
    public boolean eliminarCarrera(Long id) {
        if (carreraRepository.existsById(id)) {
            carreraRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public Carrera obtenerCarreraPorNombre(String nombreCarrera) {
        return carreraRepository.findByNombreCarrera(nombreCarrera);
    }
}
