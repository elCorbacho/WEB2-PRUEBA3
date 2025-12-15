package cl.web2.prueba3.prueba3.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.lang.NonNull;
import jakarta.servlet.http.HttpSession;
import cl.web2.prueba3.prueba3.models.*;
import cl.web2.prueba3.prueba3.services.*;
import java.util.List;

@Controller
@RequestMapping("/profesor/practicas")
public class ProfesorPracticaController {
    
    @Autowired
    private PracticaService practicaService;
    
    @Autowired
    private EstudianteService estudianteService;
    
    @Autowired
    private EmpresaService empresaService;
    
    @Autowired
    private ProfesorService profesorService;
    
    @GetMapping("/agregar")
    public String mostrarFormularioAgregar(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Profesor profesor = (Profesor) session.getAttribute("usuario");
        
        if (profesor == null) {
            return "redirect:/";
        }
        
        // Obtener solo estudiantes sin práctica asociada
        List<Estudiante> estudiantesSinPractica = estudianteService.obtenerTodosLosEstudiantes();
        estudiantesSinPractica.removeIf(e -> {
            Long eId = e.getId();
            if (eId == null) return true;
            List<Practica> practicas = practicaService.obtenerPracticasPorEstudiante(eId);
            return !practicas.isEmpty();
        });
        
        model.addAttribute("practica", new Practica());
        model.addAttribute("estudiantes", estudiantesSinPractica);
        model.addAttribute("empresas", empresaService.obtenerTodasLasEmpresas());
        model.addAttribute("profesor", profesor);
        return "profesor/practicas/agregar";
    }
    
    @PostMapping("/guardar")
    public String guardarPractica(
            @ModelAttribute Practica practica,
            @RequestParam @NonNull Long estudianteId,
            @RequestParam @NonNull Long empresaId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        Profesor profesor = (Profesor) session.getAttribute("usuario");
        if (profesor == null) {
            return "redirect:/";
        }
        
        try {
            // Verificar que el estudiante y empresa existen
            Estudiante estudiante = estudianteService.obtenerEstudiante(estudianteId);
            Empresa empresa = empresaService.obtenerEmpresa(empresaId);
            
            // Validar fechas
            if (practica.getFechaInicio().isAfter(practica.getFechaFin())) {
                redirectAttributes.addFlashAttribute("error", "La fecha de inicio debe ser anterior a la fecha de fin");
                return "redirect:/profesor/practicas/agregar";
            }
            
            practica.setEstudiante(estudiante);
            practica.setEmpresa(empresa);
            practica.setProfesor(profesor);
            
            practicaService.crearPractica(practica);
            redirectAttributes.addFlashAttribute("success", "Práctica agregada correctamente");
            return "redirect:/profesor/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
            return "redirect:/profesor/practicas/agregar";
        }
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(
            @NonNull @PathVariable Long id,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        Profesor profesor = (Profesor) session.getAttribute("usuario");
        if (profesor == null) {
            return "redirect:/";
        }
        
        try {
            Practica practica = practicaService.obtenerPractica(id);
            model.addAttribute("practica", practica);
            model.addAttribute("estudiantes", estudianteService.obtenerTodosLosEstudiantes());
            model.addAttribute("empresas", empresaService.obtenerTodasLasEmpresas());
            model.addAttribute("profesores", profesorService.obtenerTodosLosProfesores());
            return "profesor/practicas/editar";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "La práctica no existe");
            return "redirect:/profesor/dashboard";
        }
    }
    
    @PostMapping("/actualizar/{id}")
    public String actualizarPractica(
            @NonNull @PathVariable Long id,
            @ModelAttribute Practica practicaActualizada,
            @RequestParam @NonNull Long estudianteId,
            @RequestParam @NonNull Long empresaId,
            @RequestParam @NonNull Long profesorId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        Profesor profesor = (Profesor) session.getAttribute("usuario");
        if (profesor == null) {
            return "redirect:/";
        }
        
        try {
            Practica practica = practicaService.obtenerPractica(id);
            Estudiante estudiante = estudianteService.obtenerEstudiante(estudianteId);
            Empresa empresa = empresaService.obtenerEmpresa(empresaId);
            Profesor profesorTemp = profesorService.obtenerProfesor(profesorId);
            
            // Validar fechas
            if (practicaActualizada.getFechaInicio().isAfter(practicaActualizada.getFechaFin())) {
                redirectAttributes.addFlashAttribute("error", "La fecha de inicio debe ser anterior a la fecha de fin");
                return "redirect:/profesor/practicas/editar/" + id;
            }
            
            practica.setEstudiante(estudiante);
            practica.setEmpresa(empresa);
            practica.setProfesor(profesorTemp);
            practica.setFechaInicio(practicaActualizada.getFechaInicio());
            practica.setFechaFin(practicaActualizada.getFechaFin());
            practica.setActividades(practicaActualizada.getActividades());
            
            practicaService.actualizarPractica(id, practica);
            redirectAttributes.addFlashAttribute("success", "Práctica actualizada correctamente");
            return "redirect:/profesor/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
            return "redirect:/profesor/dashboard";
        }
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminarPractica(
            @NonNull @PathVariable Long id,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        Profesor profesor = (Profesor) session.getAttribute("usuario");
        if (profesor == null) {
            return "redirect:/";
        }
        
        try {
            practicaService.eliminarPractica(id);
            redirectAttributes.addFlashAttribute("success", "Práctica eliminada correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/profesor/dashboard";
    }
}
