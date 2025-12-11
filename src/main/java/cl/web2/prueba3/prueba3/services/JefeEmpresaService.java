package cl.web2.prueba3.prueba3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import cl.web2.prueba3.prueba3.models.Jefe_empresa;
import cl.web2.prueba3.prueba3.repository.JefeEmpresaRepository;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class JefeEmpresaService {
    
    @Autowired
    private JefeEmpresaRepository jefeEmpresaRepository;
    
    @Transactional
    public Jefe_empresa crearJefeEmpresa(@NonNull Jefe_empresa jefe) {
        return jefeEmpresaRepository.save(jefe);
    }
    
    public Optional<Jefe_empresa> obtenerJefeEmpresa(@NonNull Long id) {
        return jefeEmpresaRepository.findById(id);
    }
    
    public List<Jefe_empresa> obtenerTodosLosJefes() {
        return (List<Jefe_empresa>) jefeEmpresaRepository.findAll();
    }
    
    @Transactional
    public Jefe_empresa actualizarJefeEmpresa(@NonNull Long id, @NonNull Jefe_empresa jefeActualizado) {
        Optional<Jefe_empresa> jefeExistente = jefeEmpresaRepository.findById(id);
        if (jefeExistente.isPresent()) {
            Jefe_empresa jefe = jefeExistente.get();
            jefe.setNombre(jefeActualizado.getNombre());
            jefe.setApellidos(jefeActualizado.getApellidos());
            jefe.setMail(jefeActualizado.getMail());
            return jefeEmpresaRepository.save(jefe);
        }
        return null;
    }
    
    @Transactional
    public boolean eliminarJefeEmpresa(@NonNull Long id) {
        if (jefeEmpresaRepository.existsById(id)) {
            jefeEmpresaRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    @Nullable
    public Jefe_empresa obtenerJefePorMail(@NonNull String mail) {
        return jefeEmpresaRepository.findByMail(mail);
    }
}
