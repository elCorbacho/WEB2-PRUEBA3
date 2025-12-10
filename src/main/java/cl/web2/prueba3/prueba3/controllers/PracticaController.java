package cl.web2.prueba3.prueba3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.web2.prueba3.prueba3.models.Practica;
import cl.web2.prueba3.prueba3.services.PracticaService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/practicas")
public class PracticaController {
    
    @Autowired
    private PracticaService practicaService;
    
    @PostMapping
    public ResponseEntity<Practica> crearPractica(@RequestBody Practica practica) {
        Practica nuevaPractica = practicaService.crearPractica(practica);
        return new ResponseEntity<>(nuevaPractica, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<Practica>> obtenerTodas() {
        List<Practica> practicas = practicaService.obtenerTodasLasPracticas();
        return new ResponseEntity<>(practicas, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Practica> obtenerPorId(@PathVariable Long id) {
        Optional<Practica> practica = practicaService.obtenerPractica(id);
        if (practica.isPresent()) {
            return new ResponseEntity<>(practica.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Practica> actualizarPractica(@PathVariable Long id, @RequestBody Practica practicaActualizada) {
        Practica practica = practicaService.actualizarPractica(id, practicaActualizada);
        if (practica != null) {
            return new ResponseEntity<>(practica, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPractica(@PathVariable Long id) {
        if (practicaService.eliminarPractica(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<Practica>> obtenerPorEstudiante(@PathVariable Long estudianteId) {
        List<Practica> practicas = practicaService.obtenerPracticasPorEstudiante(estudianteId);
        return new ResponseEntity<>(practicas, HttpStatus.OK);
    }
    
    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<Practica>> obtenerPorEmpresa(@PathVariable Long empresaId) {
        List<Practica> practicas = practicaService.obtenerPracticasPorEmpresa(empresaId);
        return new ResponseEntity<>(practicas, HttpStatus.OK);
    }
    
    @GetMapping("/profesor/{profesorId}")
    public ResponseEntity<List<Practica>> obtenerPorProfesor(@PathVariable Long profesorId) {
        List<Practica> practicas = practicaService.obtenerPracticasPorProfesor(profesorId);
        return new ResponseEntity<>(practicas, HttpStatus.OK);
    }
}
