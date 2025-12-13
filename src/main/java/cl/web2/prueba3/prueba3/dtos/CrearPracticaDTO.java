package cl.web2.prueba3.prueba3.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearPracticaDTO {
    
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;
    
    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;
    
    @NotBlank(message = "Las actividades son obligatorias")
    private String actividades;
    
    @NotNull(message = "El ID de la empresa es obligatorio")
    private Long empresaId;
    
    @NotNull(message = "El ID del profesor es obligatorio")
    private Long profesorId;
}
