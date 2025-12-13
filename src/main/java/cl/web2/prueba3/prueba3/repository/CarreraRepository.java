package cl.web2.prueba3.prueba3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.web2.prueba3.prueba3.models.Carrera;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera, Long> {
    Carrera findByNombreCarrera(String nombreCarrera);
}
