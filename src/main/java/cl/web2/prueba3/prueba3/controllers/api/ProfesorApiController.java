package cl.web2.prueba3.prueba3.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;
import cl.web2.prueba3.prueba3.models.Profesor;
import cl.web2.prueba3.prueba3.models.Practica;
import cl.web2.prueba3.prueba3.services.ProfesorService;
import cl.web2.prueba3.prueba3.services.PracticaService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/profesores")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProfesorApiController {
    
    @Autowired
    private ProfesorService profesorService;
    
    @Autowired
    private PracticaService practicaService;
    
    /**
     * Obtener todos los profesores
     */
    @GetMapping
    public ResponseEntity<List<Profesor>> obtenerTodos() {
        List<Profesor> profesores = profesorService.obtenerTodosLosProfesores();
        return ResponseEntity.ok(profesores);
    }
    
    /**
     * Obtener profesor por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Profesor> obtenerPorId(@NonNull @PathVariable Long id) {
        Optional<Profesor> profesor = profesorService.obtenerProfesor(id);
        return profesor.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }
    

    
    // ============ ENDPOINTS DE PRÁCTICAS PARA PROFESORES ============
    
    /**
     * GET: Obtener todas las prácticas del profesor
     */
    @GetMapping("/{profesorId}/practicas")
    public ResponseEntity<List<Practica>> obtenerMisPracticas(@NonNull @PathVariable Long profesorId) {
        if (profesorId == null || profesorId <= 0) {
            return ResponseEntity.badRequest().build();
        }
        // Validar que el profesor existe
        Optional<Profesor> profesor = profesorService.obtenerProfesor(profesorId);
        if (!profesor.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        List<Practica> practicas = practicaService.obtenerPracticasPorProfesor(profesorId);
        return ResponseEntity.ok(practicas);
    }
    

    
    /**
     * POST: Crear una nueva práctica
     */
    @PostMapping("/{profesorId}/practicas")
    public ResponseEntity<Practica> crearPractica(@NonNull @PathVariable Long profesorId,
                                                  @NonNull @RequestBody Practica practica) {
        if (profesorId == null || profesorId <= 0 || practica == null) {
            return ResponseEntity.badRequest().build();
        }
        
        // Validar que el profesor exista
        Optional<Profesor> profesor = profesorService.obtenerProfesor(profesorId);
        if (!profesor.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        // Validar datos requeridos de la práctica
        if (practica.getEstudiante() == null || practica.getEmpresa() == null) {
            return ResponseEntity.badRequest().build();
        }
        
        try {
            // Asignar el profesor a la práctica
            practica.setProfesor(profesor.get());
            
            // Crear la práctica
            Practica nuevaPractica = practicaService.crearPractica(practica);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPractica);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * PUT: Actualizar una práctica
     */
    @PutMapping("/{profesorId}/practicas/{practicaId}")
    public ResponseEntity<Practica> actualizarPractica(@NonNull @PathVariable Long profesorId,
                                                       @NonNull @PathVariable Long practicaId,
                                                       @NonNull @RequestBody Practica practica) {
        if (profesorId == null || practicaId == null || practica == null) {
            return ResponseEntity.badRequest().build();
        }
        
        Optional<Practica> practicaExistente = practicaService.obtenerPractica(practicaId);
        if (!practicaExistente.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        // Validar que la práctica pertenezca al profesor
        if (!practicaExistente.get().getProfesor().getId().equals(profesorId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        try {
            Practica practicaActualizada = practicaService.actualizarPractica(practicaId, practica);
            return ResponseEntity.ok(practicaActualizada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * DELETE: Eliminar una práctica
     */
    @DeleteMapping("/{profesorId}/practicas/{practicaId}")
    public ResponseEntity<Void> eliminarPractica(@NonNull @PathVariable Long profesorId,
                                                 @NonNull @PathVariable Long practicaId) {
        if (profesorId == null || practicaId == null) {
            return ResponseEntity.badRequest().build();
        }
        
        Optional<Practica> practica = practicaService.obtenerPractica(practicaId);
        if (!practica.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        // Validar que la práctica pertenezca al profesor
        if (!practica.get().getProfesor().getId().equals(profesorId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        try {
            practicaService.eliminarPractica(practicaId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
