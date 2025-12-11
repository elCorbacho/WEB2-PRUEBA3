package cl.web2.prueba3.prueba3.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import cl.web2.prueba3.prueba3.models.TipoUsuario;
import java.util.Optional;

@Repository
public interface TipoUsuarioRepository extends CrudRepository<TipoUsuario, Long> {
    Optional<TipoUsuario> findByTipo(String tipo);
}
