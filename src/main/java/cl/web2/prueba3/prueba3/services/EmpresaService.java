package cl.web2.prueba3.prueba3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.web2.prueba3.prueba3.models.Empresa;
import cl.web2.prueba3.prueba3.repository.EmpresaRepository;
import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {
    
    @Autowired
    private EmpresaRepository empresaRepository;
    
    public Empresa crearEmpresa(Empresa empresa) {
        return empresaRepository.save(empresa);
    }
    
    public Optional<Empresa> obtenerEmpresa(Long id) {
        return empresaRepository.findById(id);
    }
    
    public List<Empresa> obtenerTodasLasEmpresas() {
        return (List<Empresa>) empresaRepository.findAll();
    }
    
    public Empresa actualizarEmpresa(Long id, Empresa empresaActualizada) {
        Optional<Empresa> empresaExistente = empresaRepository.findById(id);
        if (empresaExistente.isPresent()) {
            Empresa empresa = empresaExistente.get();
            empresa.setNombre(empresaActualizada.getNombre());
            empresa.setDireccion(empresaActualizada.getDireccion());
            empresa.setTelefono(empresaActualizada.getTelefono());
            empresa.setEmail(empresaActualizada.getEmail());
            empresa.setJefe(empresaActualizada.getJefe());
            return empresaRepository.save(empresa);
        }
        return null;
    }
    
    public boolean eliminarEmpresa(Long id) {
        if (empresaRepository.existsById(id)) {
            empresaRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public Empresa obtenerEmpresaPorEmail(String email) {
        return empresaRepository.findByEmail(email);
    }
    
    public List<Empresa> obtenerEmpresasPorJefe(Long jefeId) {
        return empresaRepository.findByJefeId(jefeId);
    }
}
