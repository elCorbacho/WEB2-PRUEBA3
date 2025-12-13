package cl.web2.prueba3.prueba3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.web2.prueba3.prueba3.models.Estudiante;
import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    Optional<Estudiante> findByEmail(String email);
    List<Estudiante> findByCarreraId(Long carreraId);
}
