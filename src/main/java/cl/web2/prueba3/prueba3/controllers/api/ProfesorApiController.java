package cl.web2.prueba3.prueba3.controllers.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
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
import cl.web2.prueba3.prueba3.dtos.ProfesorDTO;
import cl.web2.prueba3.prueba3.dtos.CrearPracticaProfesorDTO;
import cl.web2.prueba3.prueba3.mappers.PracticaMapper;
import cl.web2.prueba3.prueba3.mappers.ProfesorMapper;
import cl.web2.prueba3.prueba3.exceptions.BadRequestException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/profesores")
@CrossOrigin(origins = "*", maxAge = 3600)
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfesorApiController {
    
    private final ProfesorService profesorService;
    private final PracticaService practicaService;
    private final EstudianteService estudianteService;
    private final EmpresaService empresaService;
    private final PracticaMapper practicaMapper;
    private final ProfesorMapper profesorMapper;
    
    // get profesores - MEJORADO CON DTO
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProfesorDTO>>> obtenerTodos() {
        List<ProfesorDTO> profesoresDTO = profesorService.obtenerTodosLosProfesores()
            .stream()
            .map(profesorMapper::toProfesorDTO)
            .toList();
        
        return ResponseEntity.ok(
            ApiResponse.<List<ProfesorDTO>>builder()
                .status(200)
                .message("Profesores obtenidos correctamente")
                .data(profesoresDTO)
                .timestamp(LocalDateTime.now())
                .build()
        );
    }
    //
    //
    //
    //
    //get profesor por id - MEJORADO CON DTO
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProfesorDTO>> obtenerPorId(@PathVariable Long id) {
        Profesor profesor = profesorService.obtenerProfesor(id);
        
        return ResponseEntity.ok(
            ApiResponse.<ProfesorDTO>builder()
                .status(200)
                .message("Profesor encontrado")
                .data(profesorMapper.toProfesorDTO(profesor))
                .timestamp(LocalDateTime.now())
                .build()
        );
    }
    //
    //
    //
    //
    // get practicas por profesor - MEJORADO
    @GetMapping("/{profesorId}/practicas")
    public ResponseEntity<ApiResponse<List<PracticaDTO>>> obtenerMisPracticas(@PathVariable Long profesorId) {
        profesorService.obtenerProfesor(profesorId);
        
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
    }
    //
    //
    //
    //
    //post crear practica - MEJORADO CON @Valid Y EXCEPCIONES
    @PostMapping("/{profesorId}/practicas")
    @Transactional
    public ResponseEntity<ApiResponse<PracticaDTO>> crearPractica(
            @PathVariable Long profesorId,
            @Valid @RequestBody CrearPracticaProfesorDTO dto) {
        
        Profesor profesor = profesorService.obtenerProfesor(profesorId);
        Estudiante estudiante = estudianteService.obtenerEstudiante(dto.getEstudianteId());
        Empresa empresa = empresaService.obtenerEmpresa(dto.getEmpresaId());
        
        // Validar fechas
        if (dto.getFechaInicio().isAfter(dto.getFechaFin())) {
            throw new BadRequestException("La fecha de inicio debe ser anterior a la fecha de fin");
        }
        
        Practica practica = new Practica();
        practica.setProfesor(profesor);
        practica.setEstudiante(estudiante);
        practica.setEmpresa(empresa);
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
    //
    //
    //
    //
    // put actualizar practica
    @PutMapping("/{profesorId}/practicas/{practicaId}")
    @Transactional
    public ResponseEntity<ApiResponse<PracticaDTO>> actualizarPractica(
            @PathVariable Long profesorId,
            @PathVariable Long practicaId,
            @Valid @RequestBody Practica practica) {
        
        practicaService.obtenerPractica(practicaId);
        Practica practicaActualizada = practicaService.actualizarPractica(practicaId, practica);
        
        return ResponseEntity.ok(
            ApiResponse.<PracticaDTO>builder()
                .status(200)
                .message("Práctica actualizada correctamente")
                .data(practicaMapper.toPracticaDTO(practicaActualizada))
                .timestamp(LocalDateTime.now())
                .build()
        );
    }
    //
    //
    //
    //
    // delete eliminar practica
    @DeleteMapping("/{profesorId}/practicas/{practicaId}")
    @Transactional
    public ResponseEntity<ApiResponse<String>> eliminarPractica(
            @PathVariable Long profesorId,
            @PathVariable Long practicaId) {
        
        practicaService.obtenerPractica(practicaId);
        practicaService.eliminarPractica(practicaId);
        
        return ResponseEntity.ok(
            ApiResponse.<String>builder()
                .status(200)
                .message("Práctica eliminada correctamente")
                .data("ID: " + practicaId)
                .timestamp(LocalDateTime.now())
                .build()
        );
    }
}
