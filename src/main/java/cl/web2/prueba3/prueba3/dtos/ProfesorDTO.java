package cl.web2.prueba3.prueba3.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfesorDTO {
    private Long id;
    
    @NotBlank(message = "El nombre es obligatorio")
    private String nombres;
    
    @NotBlank(message = "El apellido es obligatorio")
    private String apellidos;
    
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo debe ser válido")
    private String correoElectronico;
    
    private Integer totalPracticas;
}
