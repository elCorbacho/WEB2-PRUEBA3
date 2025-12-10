package cl.web2.prueba3.prueba3.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.web2.prueba3.prueba3.models.Carrera;
import cl.web2.prueba3.prueba3.services.CarreraService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carreras")
public class CarreraApiController {
    
    @Autowired
    private CarreraService carreraService;
    
    @PostMapping
    public ResponseEntity<Carrera> crearCarrera(@RequestBody Carrera carrera) {
        Carrera nuevaCarrera = carreraService.crearCarrera(carrera);
        return new ResponseEntity<>(nuevaCarrera, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<Carrera>> obtenerTodas() {
        List<Carrera> carreras = carreraService.obtenerTodasLasCarreras();
        return new ResponseEntity<>(carreras, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Carrera> obtenerPorId(@PathVariable Long id) {
        Optional<Carrera> carrera = carreraService.obtenerCarrera(id);
        if (carrera.isPresent()) {
            return new ResponseEntity<>(carrera.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Carrera> actualizarCarrera(@PathVariable Long id, @RequestBody Carrera carreraActualizada) {
        Carrera carrera = carreraService.actualizarCarrera(id, carreraActualizada);
        if (carrera != null) {
            return new ResponseEntity<>(carrera, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCarrera(@PathVariable Long id) {
        if (carreraService.eliminarCarrera(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Carrera> obtenerPorNombre(@PathVariable String nombre) {
        Carrera carrera = carreraService.obtenerCarreraPorNombre(nombre);
        if (carrera != null) {
            return new ResponseEntity<>(carrera, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
