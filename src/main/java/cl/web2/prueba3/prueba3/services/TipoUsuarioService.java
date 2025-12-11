package cl.web2.prueba3.prueba3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.lang.NonNull;
import cl.web2.prueba3.prueba3.models.TipoUsuario;
import cl.web2.prueba3.prueba3.repository.TipoUsuarioRepository;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TipoUsuarioService {
    
    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;
    
    @Transactional
    public TipoUsuario crear(@NonNull TipoUsuario tipoUsuario) {
        return tipoUsuarioRepository.save(tipoUsuario);
    }
    
    public Optional<TipoUsuario> obtenerPorTipo(@NonNull String tipo) {
        return tipoUsuarioRepository.findByTipo(tipo);
    }
    
    public Optional<TipoUsuario> obtenerPorId(@NonNull Long id) {
        return tipoUsuarioRepository.findById(id);
    }
}
