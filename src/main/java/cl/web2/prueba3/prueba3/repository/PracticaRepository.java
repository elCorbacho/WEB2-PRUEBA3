package cl.web2.prueba3.prueba3.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import cl.web2.prueba3.prueba3.models.Practica;
import java.util.List;

@Repository
public interface PracticaRepository extends CrudRepository<Practica, Long> {
    List<Practica> findByEstudianteId(Long estudianteId);
    List<Practica> findByEmpresaId(Long empresaId);
    List<Practica> findByProfesorId(Long profesorId);
}
