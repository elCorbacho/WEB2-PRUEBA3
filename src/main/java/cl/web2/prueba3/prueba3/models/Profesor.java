package cl.web2.prueba3.prueba3.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "profesor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profesor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombres", length = 100, nullable = false)
    private String nombres;
    
    @Column(name = "apellidos", length = 100, nullable = false)
    private String apellidos;
    
    @Column(name = "mail_profesor", length = 150, unique = true, nullable = false)
    private String correoElectronico;
    
    @Column(unique = true, nullable = false, length = 150)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_usuario_id", nullable = false)
    private TipoUsuario tipoUsuario;
    
    @Column(nullable = false)
    private boolean activo = true;
    
    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Practica> practicas;
}
