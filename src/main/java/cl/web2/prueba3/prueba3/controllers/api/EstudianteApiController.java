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
    public ResponseEntity<ApiResponse<List<PracticaDTO>>> obtenerMisPracticas(@NonNull @PathVariable Long estudianteId) {
        if (estudianteId == null || estudianteId <= 0) {
            return ResponseEntity.badRequest().body(
                ApiResponse.<List<PracticaDTO>>builder()
                    .status(400)
                    .message("ID de estudiante inválido")
                    .timestamp(LocalDateTime.now())
                    .build()
            );
        }
        
        try {
            List<Practica> practicas = practicaService.obtenerPracticasPorEstudiante(estudianteId);
            
            List<PracticaDTO> practicasDTO = practicas.stream()
                .map(practica -> new PracticaDTO(
                    practica.getId(),
                    practica.getFechaInicio(),
                    practica.getFechaFin(),
                    practica.getActividades(),
                    practica.getEmpresa().getNombre(),
                    practica.getProfesor().getNombres(),
                    practica.getProfesor().getApellidos(),
                    practica.getEmpresa().getJefe().getNombre(),
                    practica.getEmpresa().getJefe().getApellidos()
                ))
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
    

    
    //Crear práctica para un estudiante
    @PostMapping("/{estudianteId}/practicas")
    public ResponseEntity<ApiResponse<PracticaDTO>> crearPractica(@NonNull @PathVariable Long estudianteId,
                                                  @NonNull @RequestBody Practica practica) {
        if (estudianteId == null || estudianteId <= 0 || practica == null) {
            return ResponseEntity.badRequest().body(
                ApiResponse.<PracticaDTO>builder()
                    .status(400)
                    .message("Datos inválidos")
                    .timestamp(LocalDateTime.now())
                    .build()
            );
        }
        
        try {
            // Validar que el estudiante exista
            Optional<Estudiante> estudiante = estudianteService.obtenerEstudiante(estudianteId);
            if (!estudiante.isPresent()) {
                return ResponseEntity.status(404).body(
                    ApiResponse.<PracticaDTO>builder()
                        .status(404)
                        .message("Estudiante no encontrado")
                        .timestamp(LocalDateTime.now())
                        .build()
                );
            }
            
            // Obtener empresa
            if (practica.getEmpresa() == null || practica.getEmpresa().getId() == null) {
                return ResponseEntity.badRequest().body(
                    ApiResponse.<PracticaDTO>builder()
                        .status(400)
                        .message("Empresa es requerida")
                        .timestamp(LocalDateTime.now())
                        .build()
                );
            }
            
            Optional<Empresa> empresa = empresaService.obtenerEmpresa(practica.getEmpresa().getId());
            if (!empresa.isPresent()) {
                return ResponseEntity.status(404).body(
                    ApiResponse.<PracticaDTO>builder()
                        .status(404)
                        .message("Empresa no encontrada")
                        .timestamp(LocalDateTime.now())
                        .build()
                );
            }
            
            // Obtener profesor
            if (practica.getProfesor() == null || practica.getProfesor().getId() == null) {
                return ResponseEntity.badRequest().body(
                    ApiResponse.<PracticaDTO>builder()
                        .status(400)
                        .message("Profesor es requerido")
                        .timestamp(LocalDateTime.now())
                        .build()
                );
            }
            
            Optional<Profesor> profesor = profesorService.obtenerProfesor(practica.getProfesor().getId());
            if (!profesor.isPresent()) {
                return ResponseEntity.status(404).body(
                    ApiResponse.<PracticaDTO>builder()
                        .status(404)
                        .message("Profesor no encontrado")
                        .timestamp(LocalDateTime.now())
                        .build()
                );
            }
            
            // Asignar los datos
            practica.setEstudiante(estudiante.get());
            practica.setEmpresa(empresa.get());
            practica.setProfesor(profesor.get());
            
            // Crear la práctica
            Practica nuevaPractica = practicaService.crearPractica(practica);
            
            // Convertir a DTO
            PracticaDTO practicaDTO = new PracticaDTO(
                nuevaPractica.getId(),
                nuevaPractica.getFechaInicio(),
                nuevaPractica.getFechaFin(),
                nuevaPractica.getActividades(),
                nuevaPractica.getEmpresa().getNombre(),
                nuevaPractica.getProfesor().getNombres(),
                nuevaPractica.getProfesor().getApellidos(),
                nuevaPractica.getEmpresa().getJefe().getNombre(),
                nuevaPractica.getEmpresa().getJefe().getApellidos()
            );
            
            return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<PracticaDTO>builder()
                    .status(201)
                    .message("Práctica creada correctamente")
                    .data(practicaDTO)
                    .timestamp(LocalDateTime.now())
                    .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse.<PracticaDTO>builder()
                    .status(500)
                    .message("Error al crear la práctica")
                    .error(e.getMessage())
                    .timestamp(LocalDateTime.now())
                    .build()
            );
        }
    }
    

}
