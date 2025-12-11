package cl.web2.prueba3.prueba3.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;
import cl.web2.prueba3.prueba3.models.Profesor;
import cl.web2.prueba3.prueba3.models.Practica;
import cl.web2.prueba3.prueba3.models.Estudiante;
import cl.web2.prueba3.prueba3.models.Empresa;
import cl.web2.prueba3.prueba3.services.ProfesorService;
import cl.web2.prueba3.prueba3.services.PracticaService;
import cl.web2.prueba3.prueba3.services.EstudianteService;
import cl.web2.prueba3.prueba3.services.EmpresaService;
import cl.web2.prueba3.prueba3.responses.ApiResponse;
import cl.web2.prueba3.prueba3.dtos.PracticaDTO;
import cl.web2.prueba3.prueba3.mappers.PracticaMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/profesores")
@CrossOrigin(origins = "*", maxAge = 3600)
@Transactional(readOnly = true)
public class ProfesorApiController {
    
    @Autowired
    private ProfesorService profesorService;
    
    @Autowired
    private PracticaService practicaService;
    
    @Autowired
    private EstudianteService estudianteService;
    
    @Autowired
    private EmpresaService empresaService;
    
    @Autowired
    private PracticaMapper practicaMapper;
    
    // get profesores
    @GetMapping
    public ResponseEntity<ApiResponse<List<Profesor>>> obtenerTodos() {
        try {
            List<Profesor> profesores = profesorService.obtenerTodosLosProfesores();
            return ResponseEntity.ok(
                ApiResponse.<List<Profesor>>builder()
                    .status(200)
                    .message("Profesores obtenidos correctamente")
                    .data(profesores)
                    .timestamp(LocalDateTime.now())
                    .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                ApiResponse.<List<Profesor>>builder()
                    .status(500)
                    .message("Error al obtener profesores")
                    .error(e.getMessage())
                    .timestamp(LocalDateTime.now())
                    .build()
            );
        }
    }
    //
    //
    //
    //
    //get profesor por id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Profesor>> obtenerPorId(@PathVariable Long id) {
        try {
            Profesor profesor = profesorService.obtenerProfesor(id)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));
            return ResponseEntity.ok(
                ApiResponse.<Profesor>builder()
                    .status(200)
                    .message("Profesor encontrado")
                    .data(profesor)
                    .timestamp(LocalDateTime.now())
                    .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(404).body(
                ApiResponse.<Profesor>builder()
                    .status(404)
                    .message("Profesor no encontrado")
                    .error(e.getMessage())
                    .timestamp(LocalDateTime.now())
                    .build()
            );
        }
    }
    //
    //
    //
    //
    // get practicas por profesor
    @GetMapping("/{profesorId}/practicas")
    public ResponseEntity<ApiResponse<List<PracticaDTO>>> obtenerMisPracticas(@PathVariable Long profesorId) {
        try {
            profesorService.obtenerProfesor(profesorId)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));
            
            List<PracticaDTO> practicasDTO = practicaService
                .obtenerPracticasPorProfesor(profesorId)
                .stream()
                .map(practicaMapper::toPracticaDTO)
                .toList();
            
            return ResponseEntity.ok(
                ApiResponse.<List<PracticaDTO>>builder()
                    .status(200)
                    .message("Prácticas obtenidas correctamente")
                    .data(practicasDTO)
                    .timestamp(LocalDateTime.now())
                    .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                ApiResponse.<List<PracticaDTO>>builder()
                    .status(500)
                    .message("Error al obtener prácticas")
                    .error(e.getMessage())
                    .timestamp(LocalDateTime.now())
                    .build()
            );
        }
    }
    //
    //
    //
    //
    //post crear practica
    @PostMapping("/{profesorId}/practicas")
    @Transactional
    public ResponseEntity<ApiResponse<PracticaDTO>> crearPractica(
            @PathVariable Long profesorId,
            @RequestBody Practica practica) {
        try {
            Profesor profesor = profesorService.obtenerProfesor(profesorId)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));
            
            Estudiante estudiante = estudianteService.obtenerEstudiante(practica.getEstudiante().getId())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
            
            Empresa empresa = empresaService.obtenerEmpresa(practica.getEmpresa().getId())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
            
            // Validar fechas
            if (practica.getFechaInicio().isAfter(practica.getFechaFin())) {
                return ResponseEntity.badRequest().body(
                    ApiResponse.<PracticaDTO>builder()
                        .status(400)
                        .message("La fecha de inicio debe ser anterior a la fecha de fin")
                        .timestamp(LocalDateTime.now())
                        .build()
                );
            }
            
            practica.setProfesor(profesor);
            practica.setEstudiante(estudiante);
            practica.setEmpresa(empresa);
            
            Practica nuevaPractica = practicaService.crearPractica(practica);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<PracticaDTO>builder()
                    .status(201)
                    .message("Práctica creada correctamente")
                    .data(practicaMapper.toPracticaDTO(nuevaPractica))
                    .timestamp(LocalDateTime.now())
                    .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse.<PracticaDTO>builder()
                    .status(500)
                    .message("Error al crear la práctica: " + e.getMessage())
                    .timestamp(LocalDateTime.now())
                    .build()
            );
        }
    }
    //
    //
    //
    //
    // put actualizar practica
    @PutMapping("/{profesorId}/practicas/{practicaId}")
    @Transactional
    public ResponseEntity<ApiResponse<PracticaDTO>> actualizarPractica
        (@NonNull @PathVariable Long profesorId,
            @NonNull @PathVariable Long practicaId,
            @NonNull @RequestBody Practica practica) {
        if (profesorId == null || practicaId == null || practica == null) {
            return ResponseEntity.badRequest().body(
                ApiResponse.<PracticaDTO>builder()
                    .status(400)
                    .message("Datos inválidos")
                    .timestamp(LocalDateTime.now())
                    .build()
            );
        }
        
        try {
            Optional<Practica> practicaExistente = practicaService.obtenerPractica(practicaId);
            if (!practicaExistente.isPresent()) {
                return ResponseEntity.status(404).body(
                    ApiResponse.<PracticaDTO>builder()
                        .status(404)
                        .message("Práctica no encontrada")
                        .timestamp(LocalDateTime.now())
                        .build()
                );
            }
            
            // Validar que la práctica pertenezca al profesor
            if (!practicaExistente.get().getProfesor().getId().equals(profesorId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    ApiResponse.<PracticaDTO>builder()
                        .status(403)
                        .message("No tienes permiso para editar esta práctica")
                        .timestamp(LocalDateTime.now())
                        .build()
                );
            }
            
            Practica practicaActualizada = practicaService.actualizarPractica(practicaId, practica);
            return ResponseEntity.ok(
                ApiResponse.<PracticaDTO>builder()
                    .status(200)
                    .message("Práctica actualizada correctamente")
                    .data(practicaMapper.toPracticaDTO(practicaActualizada))
                    .timestamp(LocalDateTime.now())
                    .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse.<PracticaDTO>builder()
                    .status(500)
                    .message("Error al actualizar la práctica")
                    .error(e.getMessage())
                    .timestamp(LocalDateTime.now())
                    .build()
            );
        }
    }
    //
    //
    //
    //
    // delete eliminar practica
    @DeleteMapping("/{profesorId}/practicas/{practicaId}")
    public ResponseEntity<ApiResponse<String>> eliminarPractica(@NonNull @PathVariable Long profesorId,
                                                                @NonNull @PathVariable Long practicaId) {
        if (profesorId == null || practicaId == null) {
            return ResponseEntity.badRequest().body(
                ApiResponse.<String>builder()
                    .status(400)
                    .message("El ID del profesor y la práctica son requeridos")
                    .timestamp(LocalDateTime.now())
                    .build()
            );
        }
        
        try {
            Optional<Practica> practica = practicaService.obtenerPractica(practicaId);
            if (!practica.isPresent()) {
                return ResponseEntity.status(404).body(
                    ApiResponse.<String>builder()
                        .status(404)
                        .message("Práctica no encontrada")
                        .timestamp(LocalDateTime.now())
                        .build()
                );
            }
            
            practicaService.eliminarPractica(practicaId);
            return ResponseEntity.ok(
                ApiResponse.<String>builder()
                    .status(200)
                    .message("Práctica eliminada correctamente")
                    .data("ID: " + practicaId)
                    .timestamp(LocalDateTime.now())
                    .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse.<String>builder()
                    .status(500)
                    .message("Error al eliminar la práctica")
                    .error(e.getMessage())
                    .timestamp(LocalDateTime.now())
                    .build()
            );
        }
    }
}
