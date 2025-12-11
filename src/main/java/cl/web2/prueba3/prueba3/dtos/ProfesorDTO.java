package cl.web2.prueba3.prueba3.dtos;

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
    private String nombres;
    private String apellidos;
    private String correoElectronico;
    private Integer totalPracticas;
}
