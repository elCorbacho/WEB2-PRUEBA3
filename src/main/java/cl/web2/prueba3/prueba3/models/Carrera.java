package cl.web2.prueba3.prueba3.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "carrera")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carrera {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre_carrera", length = 150, nullable = false)
    private String nombreCarrera;
    
    @OneToMany(mappedBy = "carrera", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Estudiante> estudiantes;
}
