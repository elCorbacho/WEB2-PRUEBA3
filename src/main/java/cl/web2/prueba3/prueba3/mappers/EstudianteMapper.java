package cl.web2.prueba3.prueba3.mappers;

import org.springframework.stereotype.Component;
import cl.web2.prueba3.prueba3.models.Estudiante;
import cl.web2.prueba3.prueba3.dtos.EstudianteDTO;

@Component
public class EstudianteMapper {
    
    public EstudianteDTO toEstudianteDTO(Estudiante estudiante) {
        if (estudiante == null) {
            return null;
        }
        
        return EstudianteDTO.builder()
            .id(estudiante.getId())
            .nombre(estudiante.getNombre())
            .apellido(estudiante.getApellido())
            .email(estudiante.getEmail())
            .carreraNombre(estudiante.getCarrera() != null ? estudiante.getCarrera().getNombreCarrera() : null)
            .carreraId(estudiante.getCarrera() != null ? estudiante.getCarrera().getId() : null)
            .build();
    }
    
    public Estudiante toEstudiante(EstudianteDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Estudiante estudiante = new Estudiante();
        estudiante.setId(dto.getId());
        estudiante.setNombre(dto.getNombre());
        estudiante.setApellido(dto.getApellido());
        estudiante.setEmail(dto.getEmail());
        
        return estudiante;
    }
}
