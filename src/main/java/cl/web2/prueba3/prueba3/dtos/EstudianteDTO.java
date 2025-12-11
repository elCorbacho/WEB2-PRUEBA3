package cl.web2.prueba3.prueba3.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String carreraNombre;
    private Long carreraId;
}
