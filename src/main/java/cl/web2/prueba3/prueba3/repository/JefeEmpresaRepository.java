package cl.web2.prueba3.prueba3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.web2.prueba3.prueba3.models.Jefe_empresa;

@Repository
public interface JefeEmpresaRepository extends JpaRepository<Jefe_empresa, Long> {
    Jefe_empresa findByMail(String mail);
}
