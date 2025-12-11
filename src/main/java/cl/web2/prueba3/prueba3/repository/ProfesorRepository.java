package cl.web2.prueba3.prueba3.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import cl.web2.prueba3.prueba3.models.Profesor;
import java.util.Optional;

@Repository
public interface ProfesorRepository extends CrudRepository<Profesor, Long> {
    Profesor findByCorreoElectronico(String correoElectronico);
    Optional<Profesor> findByEmail(String email);
}
