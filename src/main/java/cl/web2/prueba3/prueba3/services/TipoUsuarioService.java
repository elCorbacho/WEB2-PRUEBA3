package cl.web2.prueba3.prueba3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.web2.prueba3.prueba3.models.TipoUsuario;
import cl.web2.prueba3.prueba3.repository.TipoUsuarioRepository;
import java.util.Optional;

@Service
public class TipoUsuarioService {
    
    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;
    
    public TipoUsuario crear(TipoUsuario tipoUsuario) {
        return tipoUsuarioRepository.save(tipoUsuario);
    }
    
    public Optional<TipoUsuario> obtenerPorTipo(String tipo) {
        return tipoUsuarioRepository.findByTipo(tipo);
    }
    
    public Optional<TipoUsuario> obtenerPorId(Long id) {
        return tipoUsuarioRepository.findById(id);
    }
}
