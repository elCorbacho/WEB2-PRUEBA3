package cl.web2.prueba3.prueba3.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

@Entity
@Table(name = "practicas_profesionales")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Practica {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id", nullable = false)
    @JsonIgnore
    private Estudiante estudiante;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id", nullable = false)
    @JsonProperty("empresa")  // ← AGREGA ESTO
    private Empresa empresa;
    
    @Column(name = "fecha_inicio", nullable = false)
    @JsonProperty("fechaInicio")  // ← AGREGA ESTO
    private LocalDate fechaInicio;
    
    @Column(name = "fecha_fin", nullable = false)
    @JsonProperty("fechaFin")  // ← AGREGA ESTO
    private LocalDate fechaFin;
    
    @Column(name = "actividades", columnDefinition = "TEXT")
    @JsonProperty("actividades")  // ← AGREGA ESTO
    private String actividades;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profesor_id", nullable = false)
    @JsonProperty("profesor")  // ← AGREGA ESTO
    private Profesor profesor;
}
