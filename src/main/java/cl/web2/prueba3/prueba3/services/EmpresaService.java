package cl.web2.prueba3.prueba3.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.lang.NonNull;
import cl.web2.prueba3.prueba3.models.Empresa;
import cl.web2.prueba3.prueba3.repository.EmpresaRepository;
import cl.web2.prueba3.prueba3.exceptions.ResourceNotFoundException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmpresaService {
    
    private final EmpresaRepository empresaRepository;
    
    @Transactional
    public Empresa crearEmpresa(@NonNull Empresa empresa) {
        return empresaRepository.save(empresa);
    }
    
    public Empresa obtenerEmpresa(@NonNull Long id) {
        return empresaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Empresa", "id", id));
    }
    
    public List<Empresa> obtenerTodasLasEmpresas() {
        return empresaRepository.findAll();
    }
    
    @Transactional
    public Empresa actualizarEmpresa(@NonNull Long id, @NonNull Empresa empresaActualizada) {
        Empresa empresa = obtenerEmpresa(id);
        empresa.setNombre(empresaActualizada.getNombre());
        empresa.setDireccion(empresaActualizada.getDireccion());
        empresa.setTelefono(empresaActualizada.getTelefono());
        empresa.setEmail(empresaActualizada.getEmail());
        empresa.setJefe(empresaActualizada.getJefe());
        return empresaRepository.save(empresa);
    }
    
    @Transactional
    public void eliminarEmpresa(@NonNull Long id) {
        if (!empresaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Empresa", "id", id);
        }
        empresaRepository.deleteById(id);
    }
    
    public Empresa obtenerEmpresaPorEmail(@NonNull String email) {
        Empresa empresa = empresaRepository.findByEmail(email);
        if (empresa == null) {
            throw new ResourceNotFoundException("Empresa", "email", email);
        }
        return empresa;
    }
    
    public List<Empresa> obtenerEmpresasPorJefe(@NonNull Long jefeId) {
        return empresaRepository.findByJefeId(jefeId);
    }
}
