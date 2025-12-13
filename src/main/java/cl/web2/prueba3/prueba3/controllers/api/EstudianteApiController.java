package cl.web2.prueba3.prueba3.controllers.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import cl.web2.prueba3.prueba3.models.Estudiante;
import cl.web2.prueba3.prueba3.models.Practica;
import cl.web2.prueba3.prueba3.models.Empresa;
import cl.web2.prueba3.prueba3.models.Profesor;
import cl.web2.prueba3.prueba3.dtos.PracticaDTO;
import cl.web2.prueba3.prueba3.dtos.EstudianteDTO;
import cl.web2.prueba3.prueba3.dtos.CrearPracticaDTO;
import cl.web2.prueba3.prueba3.services.EstudianteService;
import cl.web2.prueba3.prueba3.services.PracticaService;
import cl.web2.prueba3.prueba3.services.EmpresaService;
import cl.web2.prueba3.prueba3.services.ProfesorService;
import cl.web2.prueba3.prueba3.responses.ApiResponse;
import cl.web2.prueba3.prueba3.mappers.PracticaMapper;
import cl.web2.prueba3.prueba3.mappers.EstudianteMapper;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin(origins = "*", maxAge = 3600)
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EstudianteApiController {
    
    private final EstudianteService estudianteService;
    private final PracticaService practicaService;
    private final EmpresaService empresaService;
    private final ProfesorService profesorService;
    private final PracticaMapper practicaMapper;
    private final EstudianteMapper estudianteMapper;
    
    //--------------------------------------------------------------------------------
    //Obtener todos los estudiantes - MEJORADO CON DTO
    @GetMapping
    public ResponseEntity<ApiResponse<List<EstudianteDTO>>> obtenerTodos() {
        List<EstudianteDTO> estudiantesDTO = estudianteService.obtenerTodosLosEstudiantes()
            .stream()
            .map(estudianteMapper::toEstudianteDTO)
            .toList();
            
        return ResponseEntity.ok(
            ApiResponse.<List<EstudianteDTO>>builder()
                .status(200)
                .message("Estudiantes obtenidos correctamente")
                .data(estudiantesDTO)
                .timestamp(LocalDateTime.now())
                .build()
        );
    }
     //--------------------------------------------------------------------------------
    
      //--------------------------------------------------------------------------------
    // Obtener estudiante por ID - MEJORADO CON DTO
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EstudianteDTO>> obtenerPorId(@PathVariable Long id) {
        Estudiante estudiante = estudianteService.obtenerEstudiante(id);
        return ResponseEntity.ok(
            ApiResponse.<EstudianteDTO>builder()
                .status(200)
                .message("Estudiante encontrado")
                .data(estudianteMapper.toEstudianteDTO(estudiante))
                .timestamp(LocalDateTime.now())
                .build()
        );
    }
     //--------------------------------------------------------------------------------
    
    
     //--------------------------------------------------------------------------------
    // Obtener prácticas de un estudiante
    @GetMapping("/{estudianteId}/practicas")
    public ResponseEntity<ApiResponse<List<PracticaDTO>>> obtenerMisPracticas(@PathVariable Long estudianteId) {
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
    }
     //--------------------------------------------------------------------------------
    

    
    // Crear práctica para un estudiante - MEJORADO CON @Valid
    @PostMapping("/{estudianteId}/practicas")
    @Transactional
    public ResponseEntity<ApiResponse<PracticaDTO>> crearPractica(
            @PathVariable Long estudianteId,
            @Valid @RequestBody CrearPracticaDTO dto) {
        
        Estudiante estudiante = estudianteService.obtenerEstudiante(estudianteId);
        Empresa empresa = empresaService.obtenerEmpresa(dto.getEmpresaId());
        Profesor profesor = profesorService.obtenerProfesor(dto.getProfesorId());
        
        Practica practica = new Practica();
        practica.setEstudiante(estudiante);
        practica.setEmpresa(empresa);
        practica.setProfesor(profesor);
        practica.setFechaInicio(dto.getFechaInicio());
        practica.setFechaFin(dto.getFechaFin());
        practica.setActividades(dto.getActividades());
        
        Practica nuevaPractica = practicaService.crearPractica(practica);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse.<PracticaDTO>builder()
                .status(201)
                .message("Práctica creada correctamente")
                .data(practicaMapper.toPracticaDTO(nuevaPractica))
                .timestamp(LocalDateTime.now())
                .build()
        );
    }
    

}
