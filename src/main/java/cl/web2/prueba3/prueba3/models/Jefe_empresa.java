package cl.web2.prueba3.prueba3.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "jefe_empresa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jefe_empresa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;
    
    @Column(name = "apellidos", length = 100, nullable = false)
    private String apellidos;
    
    @Column(name = "mail", length = 150, unique = true, nullable = false)
    private String mail;
    
    @OneToMany(mappedBy = "jefe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Empresa> empresas;
}
