package cl.web2.prueba3.prueba3.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "empresas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre", length = 150, nullable = false)
    private String nombre;
    
    @Column(name = "direccion", length = 200)
    private String direccion;
    
    @Column(name = "telefono", length = 20)
    private String telefono;
    
    @Column(name = "email", length = 150, unique = true)
    private String email;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jefe_id", nullable = false)
    private Jefe_empresa jefe;
    
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Practica> practicas;
}
