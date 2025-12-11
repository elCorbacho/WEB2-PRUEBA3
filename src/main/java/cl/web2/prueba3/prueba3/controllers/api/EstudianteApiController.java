package cl.web2.prueba3.prueba3.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;
import cl.web2.prueba3.prueba3.models.Estudiante;
import cl.web2.prueba3.prueba3.models.Practica;
import cl.web2.prueba3.prueba3.models.Empresa;
import cl.web2.prueba3.prueba3.models.Profesor;
import cl.web2.prueba3.prueba3.dtos.PracticaDTO;
import cl.web2.prueba3.prueba3.services.EstudianteService;
import cl.web2.prueba3.prueba3.services.PracticaService;
import cl.web2.prueba3.prueba3.services.EmpresaService;
import cl.web2.prueba3.prueba3.services.ProfesorService;
import cl.web2.prueba3.prueba3.responses.ApiResponse;
import cl.web2.prueba3.prueba3.mappers.PracticaMapper;
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
    
    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private ProfesorService profesorService;
    
    @Autowired
    private PracticaMapper practicaMapper;
    
    //--------------------------------------------------------------------------------
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
     //--------------------------------------------------------------------------------
    
      //--------------------------------------------------------------------------------
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
     //--------------------------------------------------------------------------------
    
    
     //--------------------------------------------------------------------------------
    // Obtener prácticas de un estudiante
    @GetMapping("/{estudianteId}/practicas")
    public ResponseEntity<ApiResponse<List<PracticaDTO>>> obtenerMisPracticas(@PathVariable Long estudianteId) {
        try {
            List<PracticaDTO> practicasDTO = practicaService
                .obtenerPracticasPorEstudiante(estudianteId)
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
     //--------------------------------------------------------------------------------
    

    
    // Crear práctica para un estudiante
    @PostMapping("/{estudianteId}/practicas")
    public ResponseEntity<ApiResponse<PracticaDTO>> crearPractica(
            @PathVariable Long estudianteId,
            @RequestBody Practica practica) {
        try {
            Estudiante estudiante = estudianteService.obtenerEstudiante(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
            
            Empresa empresa = empresaService.obtenerEmpresa(practica.getEmpresa().getId())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
            
            Profesor profesor = profesorService.obtenerProfesor(practica.getProfesor().getId())
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));
            
            practica.setEstudiante(estudiante);
            practica.setEmpresa(empresa);
            practica.setProfesor(profesor);
            
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
    

}
