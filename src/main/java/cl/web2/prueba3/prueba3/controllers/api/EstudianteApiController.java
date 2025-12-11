package cl.web2.prueba3.prueba3.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;
import cl.web2.prueba3.prueba3.models.Estudiante;
import cl.web2.prueba3.prueba3.models.Practica;
import cl.web2.prueba3.prueba3.services.EstudianteService;
import cl.web2.prueba3.prueba3.services.PracticaService;
import cl.web2.prueba3.prueba3.responses.ApiResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EstudianteApiController {
    
    @Autowired
    private EstudianteService estudianteService;
    
    @Autowired
    private PracticaService practicaService;
    
    //Obtener todos los estudiantes
    @GetMapping
    public ResponseEntity<ApiResponse<List<Estudiante>>> obtenerTodos() {
        List<Estudiante> estudiantes = estudianteService.obtenerTodosLosEstudiantes();
        return ResponseEntity.ok(
            ApiResponse.<List<Estudiante>>builder()
                .status(200)
                .message("Estudiantes obtenidos correctamente")
                .data(estudiantes)
                .timestamp(LocalDateTime.now())
                .build()
        );
    }
    
    // Obtener estudiante por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Estudiante>> obtenerPorId(@NonNull @PathVariable Long id) {
        Optional<Estudiante> estudiante = estudianteService.obtenerEstudiante(id);
        if (estudiante.isPresent()) {
            return ResponseEntity.ok(
                ApiResponse.<Estudiante>builder()
                    .status(200)
                    .message("Estudiante encontrado")
                    .data(estudiante.get())
                    .timestamp(LocalDateTime.now())
                    .build()
            );
        } else {
            return ResponseEntity.status(404).body(
                ApiResponse.<Estudiante>builder()
                    .status(404)
                    .message("Estudiante no encontrado")
                    .timestamp(LocalDateTime.now())
                    .build()
            );
        }
    }
    
    
    
    // Obtener prácticas de un estudiante
    @GetMapping("/{estudianteId}/practicas")
    public ResponseEntity<ApiResponse<List<Practica>>> obtenerMisPracticas(@NonNull @PathVariable Long estudianteId) {
        if (estudianteId == null || estudianteId <= 0) {
            return ResponseEntity.badRequest().body(
                ApiResponse.<List<Practica>>builder()
                    .status(400)
                    .message("ID de estudiante inválido")
                    .timestamp(LocalDateTime.now())
                    .build()
            );
        }
        List<Practica> practicas = practicaService.obtenerPracticasPorEstudiante(estudianteId);
        return ResponseEntity.ok(
            ApiResponse.<List<Practica>>builder()
                .status(200)
                .message("Prácticas obtenidas correctamente")
                .data(practicas)
                .timestamp(LocalDateTime.now())
                .build()
        );
    }
    
    //Crear práctica para un estudiante
    @PostMapping("/{estudianteId}/practicas")
    public ResponseEntity<ApiResponse<Practica>> crearPractica(@NonNull @PathVariable Long estudianteId,
                                                  @NonNull @RequestBody Practica practica) {
        if (estudianteId == null || estudianteId <= 0 || practica == null) {
            return ResponseEntity.badRequest().body(
                ApiResponse.<Practica>builder()
                    .status(400)
                    .message("Datos inválidos")
                    .timestamp(LocalDateTime.now())
                    .build()
            );
        }
        
        // Validar que el estudiante exista
        Optional<Estudiante> estudiante = estudianteService.obtenerEstudiante(estudianteId);
        if (!estudiante.isPresent()) {
            return ResponseEntity.status(404).body(
                ApiResponse.<Practica>builder()
                    .status(404)
                    .message("Estudiante no encontrado")
                    .timestamp(LocalDateTime.now())
                    .build()
            );
        }
        
        try {
            // Asignar el estudiante a la práctica
            practica.setEstudiante(estudiante.get());
            
            // Crear la práctica
            Practica nuevaPractica = practicaService.crearPractica(practica);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<Practica>builder()
                    .status(201)
                    .message("Práctica creada correctamente")
                    .data(nuevaPractica)
                    .timestamp(LocalDateTime.now())
                    .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse.<Practica>builder()
                    .status(500)
                    .message("Error al crear la práctica")
                    .error(e.getMessage())
                    .timestamp(LocalDateTime.now())
                    .build()
            );
        }
    }
    

}
