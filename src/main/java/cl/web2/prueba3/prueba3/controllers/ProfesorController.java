package cl.web2.prueba3.prueba3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.web2.prueba3.prueba3.models.Profesor;
import cl.web2.prueba3.prueba3.services.ProfesorService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/profesores")
public class ProfesorController {
    
    @Autowired
    private ProfesorService profesorService;
    
    @PostMapping
    public ResponseEntity<Profesor> crearProfesor(@RequestBody Profesor profesor) {
        Profesor nuevoProfesor = profesorService.crearProfesor(profesor);
        return new ResponseEntity<>(nuevoProfesor, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<Profesor>> obtenerTodos() {
        List<Profesor> profesores = profesorService.obtenerTodosLosProfesores();
        return new ResponseEntity<>(profesores, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Profesor> obtenerPorId(@PathVariable Long id) {
        Optional<Profesor> profesor = profesorService.obtenerProfesor(id);
        if (profesor.isPresent()) {
            return new ResponseEntity<>(profesor.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Profesor> actualizarProfesor(@PathVariable Long id, @RequestBody Profesor profesorActualizado) {
        Profesor profesor = profesorService.actualizarProfesor(id, profesorActualizado);
        if (profesor != null) {
            return new ResponseEntity<>(profesor, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProfesor(@PathVariable Long id) {
        if (profesorService.eliminarProfesor(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/correo/{correo}")
    public ResponseEntity<Profesor> obtenerPorCorreo(@PathVariable String correo) {
        Profesor profesor = profesorService.obtenerProfesorPorCorreo(correo);
        if (profesor != null) {
            return new ResponseEntity<>(profesor, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
