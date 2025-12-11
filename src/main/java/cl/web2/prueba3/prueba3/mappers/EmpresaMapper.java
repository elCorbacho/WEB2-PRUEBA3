package cl.web2.prueba3.prueba3.mappers;

import org.springframework.stereotype.Component;
import cl.web2.prueba3.prueba3.models.Empresa;
import cl.web2.prueba3.prueba3.dtos.EmpresaDTO;

@Component
public class EmpresaMapper {
    
    public EmpresaDTO toEmpresaDTO(Empresa empresa) {
        if (empresa == null) {
            return null;
        }
        
        return EmpresaDTO.builder()
            .id(empresa.getId())
            .nombre(empresa.getNombre())
            .direccion(empresa.getDireccion())
            .telefono(empresa.getTelefono())
            .email(empresa.getEmail())
            .jefeNombre(empresa.getJefe() != null ? empresa.getJefe().getNombre() + " " + empresa.getJefe().getApellidos() : null)
            .jefeId(empresa.getJefe() != null ? empresa.getJefe().getId() : null)
            .build();
    }
    
    public Empresa toEmpresa(EmpresaDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Empresa empresa = new Empresa();
        empresa.setId(dto.getId());
        empresa.setNombre(dto.getNombre());
        empresa.setDireccion(dto.getDireccion());
        empresa.setTelefono(dto.getTelefono());
        empresa.setEmail(dto.getEmail());
        
        return empresa;
    }
}
