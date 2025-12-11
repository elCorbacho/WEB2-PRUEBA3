package cl.web2.prueba3.prueba3.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import cl.web2.prueba3.prueba3.models.Practica;
import cl.web2.prueba3.prueba3.models.Estudiante;
import cl.web2.prueba3.prueba3.services.PracticaService;
import cl.web2.prueba3.prueba3.services.EmpresaService;
import cl.web2.prueba3.prueba3.services.ProfesorService;
import java.util.Optional;

@Controller
@RequestMapping("/practicas")
public class PracticaController {
    
    @Autowired
    private PracticaService practicaService;
    
    @Autowired
    private EmpresaService empresaService;
    
    @Autowired
    private ProfesorService profesorService;
    
    /**
     * Mostrar formulario para crear nueva práctica (solo para estudiantes)
     */
    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        // Validar que sea estudiante
        Estudiante estudiante = (Estudiante) session.getAttribute("usuario");
        if (estudiante == null) {
            return "redirect:/";
        }
        
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        if (tipoUsuario == null || !tipoUsuario.equals("ESTUDIANTE")) {
            return "redirect:/profesor/dashboard";
        }
        
        // Validar si el estudiante tiene una práctica en curso
        if (practicaService.tienePracticaEnCurso(estudiante.getId())) {
            Optional<Practica> practicaEnCurso = practicaService.obtenerPracticaEnCurso(estudiante.getId());
            redirectAttributes.addFlashAttribute("mensaje", "No puedes crear una nueva práctica. Ya tienes una en curso en " + 
                                                 (practicaEnCurso.isPresent() ? practicaEnCurso.get().getEmpresa().getNombre() : "una empresa"));
            redirectAttributes.addFlashAttribute("tipo", "warning");
            return "redirect:/estudiante/dashboard";
        }
        
        model.addAttribute("practica", new Practica());
        model.addAttribute("estudiante", estudiante);
        model.addAttribute("empresas", empresaService.obtenerTodasLasEmpresas());
        model.addAttribute("profesores", profesorService.obtenerTodosLosProfesores());
        model.addAttribute("accion", "Crear");
        
        return "estudiante/practicas/crear";
    }
    
    /**
     * Guardar nueva práctica
     */
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Practica practica, 
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        try {
            // Obtener estudiante de la sesión
            Estudiante estudiante = (Estudiante) session.getAttribute("usuario");
            
            if (estudiante == null) {
                redirectAttributes.addFlashAttribute("mensaje", "Estudiante no encontrado");
                redirectAttributes.addFlashAttribute("tipo", "danger");
                return "redirect:/estudiante/dashboard";
            }
            
            // Validar nuevamente si hay práctica en curso
            if (practicaService.tienePracticaEnCurso(estudiante.getId())) {
                redirectAttributes.addFlashAttribute("mensaje", "No puedes crear una nueva práctica. Ya tienes una en curso");
                redirectAttributes.addFlashAttribute("tipo", "warning");
                return "redirect:/estudiante/dashboard";
            }
            
            // Asignar estudiante automáticamente
            practica.setEstudiante(estudiante);
            
            // Validar fechas
            if (practica.getFechaInicio().isAfter(practica.getFechaFin())) {
                redirectAttributes.addFlashAttribute("mensaje", "La fecha de inicio debe ser anterior a la fecha de fin");
                redirectAttributes.addFlashAttribute("tipo", "danger");
                return "redirect:/practicas/crear";
            }
            
            practicaService.crearPractica(practica);
            redirectAttributes.addFlashAttribute("mensaje", "Práctica creada exitosamente");
            redirectAttributes.addFlashAttribute("tipo", "success");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al crear la práctica: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "danger");
            return "redirect:/practicas/crear";
        }
        return "redirect:/estudiante/dashboard";
    }
    
    /**
     * Listar prácticas del estudiante
     */
    @GetMapping("/mis-practicas")
    public String misPracticas(Model model, HttpSession session) {
        Estudiante estudiante = (Estudiante) session.getAttribute("usuario");
        if (estudiante == null) {
            return "redirect:/";
        }
        
        Long estudianteId = estudiante.getId();
        model.addAttribute("practicas", practicaService.obtenerPracticasPorEstudiante(estudianteId));
        model.addAttribute("estudiante", estudiante);
        model.addAttribute("tienePracticaEnCurso", practicaService.tienePracticaEnCurso(estudianteId));
        
        return "estudiante/practicas/lista";
    }
    
    @GetMapping
    public String listar(Model model, HttpSession session) {
        Object usuario = session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/";
        }
        model.addAttribute("practicas", practicaService.obtenerTodasLasPracticas());
        return "practica/lista";
    }
}

