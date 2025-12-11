package cl.web2.prueba3.prueba3.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "estudiantes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estudiante {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;
    
    @Column(name = "apellido", length = 100, nullable = false)
    private String apellido;
    
    @Column(name = "mail_estudiante", length = 150, unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "carrera_id", nullable = false)
    @JsonIgnore
    private Carrera carrera;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_usuario_id", nullable = false)
    @JsonIgnore
    private TipoUsuario tipoUsuario;
    
    @Column(nullable = false)
    private boolean activo = true;
    
    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Practica> practicas;
}
