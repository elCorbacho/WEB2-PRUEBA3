package cl.web2.prueba3.prueba3.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private String jefeNombre;
    private Long jefeId;
}
