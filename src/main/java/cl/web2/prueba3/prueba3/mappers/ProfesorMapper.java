package cl.web2.prueba3.prueba3.mappers;

import org.springframework.stereotype.Component;
import cl.web2.prueba3.prueba3.models.Profesor;
import cl.web2.prueba3.prueba3.dtos.ProfesorDTO;

@Component
public class ProfesorMapper {
    
    public ProfesorDTO toProfesorDTO(Profesor profesor) {
        if (profesor == null) {
            return null;
        }
        
        return ProfesorDTO.builder()
            .id(profesor.getId())
            .nombres(profesor.getNombres())
            .apellidos(profesor.getApellidos())
            .correoElectronico(profesor.getCorreoElectronico())
            .totalPracticas(profesor.getPracticas() != null ? profesor.getPracticas().size() : 0)
            .build();
    }
    
    public Profesor toProfesor(ProfesorDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Profesor profesor = new Profesor();
        profesor.setId(dto.getId());
        profesor.setNombres(dto.getNombres());
        profesor.setApellidos(dto.getApellidos());
        profesor.setCorreoElectronico(dto.getCorreoElectronico());
        
        return profesor;
    }
}
