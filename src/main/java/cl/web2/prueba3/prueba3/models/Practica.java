package cl.web2.prueba3.prueba3.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    
    @NotNull(message = "El estudiante es obligatorio")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estudiante_id", nullable = false)
    @JsonProperty("estudiante")
    private Estudiante estudiante;
    
    @NotNull(message = "La empresa es obligatoria")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id", nullable = false)
    @JsonProperty("empresa")
    private Empresa empresa;
    
    @NotNull(message = "La fecha de inicio es obligatoria")
    @Column(name = "fecha_inicio", nullable = false)
    @JsonProperty("fechaInicio")
    private LocalDate fechaInicio;
    
    @NotNull(message = "La fecha de fin es obligatoria")
    @Column(name = "fecha_fin", nullable = false)
    @JsonProperty("fechaFin")
    private LocalDate fechaFin;
    
    @Column(name = "actividades", columnDefinition = "TEXT")
    @JsonProperty("actividades")
    private String actividades;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profesor_id", nullable = false)
    @JsonProperty("profesor")
    private Profesor profesor;
}
