package cl.web2.prueba3.prueba3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.web2.prueba3.prueba3.models.Empresa;
import java.util.List;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    Empresa findByEmail(String email);
    List<Empresa> findByJefeId(Long jefeId);
}
