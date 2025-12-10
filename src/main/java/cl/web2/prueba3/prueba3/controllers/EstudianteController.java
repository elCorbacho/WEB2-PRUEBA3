package cl.web2.prueba3.prueba3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.web2.prueba3.prueba3.models.Estudiante;
import cl.web2.prueba3.prueba3.services.EstudianteService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {
    
    @Autowired
    private EstudianteService estudianteService;
    
    @PostMapping
    public ResponseEntity<Estudiante> crearEstudiante(@RequestBody Estudiante estudiante) {
        Estudiante nuevoEstudiante = estudianteService.crearEstudiante(estudiante);
        return new ResponseEntity<>(nuevoEstudiante, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<Estudiante>> obtenerTodos() {
        List<Estudiante> estudiantes = estudianteService.obtenerTodosLosEstudiantes();
        return new ResponseEntity<>(estudiantes, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> obtenerPorId(@PathVariable Long id) {
        Optional<Estudiante> estudiante = estudianteService.obtenerEstudiante(id);
        if (estudiante.isPresent()) {
            return new ResponseEntity<>(estudiante.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> actualizarEstudiante(@PathVariable Long id, @RequestBody Estudiante estudianteActualizado) {
        Estudiante estudiante = estudianteService.actualizarEstudiante(id, estudianteActualizado);
        if (estudiante != null) {
            return new ResponseEntity<>(estudiante, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEstudiante(@PathVariable Long id) {
        if (estudianteService.eliminarEstudiante(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<Estudiante> obtenerPorEmail(@PathVariable String email) {
        Estudiante estudiante = estudianteService.obtenerEstudiantePorEmail(email);
        if (estudiante != null) {
            return new ResponseEntity<>(estudiante, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/carrera/{carreraId}")
    public ResponseEntity<List<Estudiante>> obtenerPorCarrera(@PathVariable Long carreraId) {
        List<Estudiante> estudiantes = estudianteService.obtenerEstudiantesPorCarrera(carreraId);
        return new ResponseEntity<>(estudiantes, HttpStatus.OK);
    }
}
