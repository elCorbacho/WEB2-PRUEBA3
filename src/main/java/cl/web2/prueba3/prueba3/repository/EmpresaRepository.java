package cl.web2.prueba3.prueba3.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import cl.web2.prueba3.prueba3.models.Empresa;
import java.util.List;

@Repository
public interface EmpresaRepository extends CrudRepository<Empresa, Long> {
    Empresa findByEmail(String email);
    List<Empresa> findByJefeId(Long jefeId);
}
