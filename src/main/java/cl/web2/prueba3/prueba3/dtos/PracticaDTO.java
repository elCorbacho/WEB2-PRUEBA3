package cl.web2.prueba3.prueba3.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PracticaDTO {
    private Long id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String actividades;
    private String empresaNombre;
    private String profesorNombres;
    private String profesorApellidos;
    private String jefeNombre;
    private String jefeApellidos;
    private String estudianteNombre;
    private String estudianteApellido;
}
