package cl.web2.prueba3.prueba3.mappers;

import org.springframework.stereotype.Component;
import cl.web2.prueba3.prueba3.models.Practica;
import cl.web2.prueba3.prueba3.dtos.PracticaDTO;

@Component
public class PracticaMapper {
    
    public PracticaDTO toPracticaDTO(Practica practica) {
        return new PracticaDTO(
            practica.getId(),
            practica.getFechaInicio(),
            practica.getFechaFin(),
            practica.getActividades(),
            practica.getEmpresa().getNombre(),
            practica.getProfesor().getNombres(),
            practica.getProfesor().getApellidos(),
            practica.getEmpresa().getJefe().getNombre(),
            practica.getEmpresa().getJefe().getApellidos(),
            practica.getEstudiante().getNombre(),
            practica.getEstudiante().getApellido()
        );
    }
}
