package cl.web2.prueba3.prueba3.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import cl.web2.prueba3.prueba3.models.*;
import cl.web2.prueba3.prueba3.services.*;
import java.util.List;
import java.util.Optional;

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
            List<Practica> practicas = practicaService.obtenerPracticasPorEstudiante(e.getId());
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
            @RequestParam Long estudianteId,
            @RequestParam Long empresaId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        Profesor profesor = (Profesor) session.getAttribute("usuario");
        if (profesor == null) {
            return "redirect:/";
        }
        
        // Verificar que el estudiante existe
        Optional<Estudiante> estudiante = estudianteService.obtenerEstudiante(estudianteId);
        if (!estudiante.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "El estudiante no existe");
            return "redirect:/profesor/practicas/agregar";
        }
        
        // Verificar que la empresa existe
        Optional<Empresa> empresa = empresaService.obtenerEmpresa(empresaId);
        if (!empresa.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "La empresa no existe");
            return "redirect:/profesor/practicas/agregar";
        }
        
        // Validar fechas
        if (practica.getFechaInicio().isAfter(practica.getFechaFin())) {
            redirectAttributes.addFlashAttribute("error", "La fecha de inicio debe ser anterior a la fecha de fin");
            return "redirect:/profesor/practicas/agregar";
        }
        
        practica.setEstudiante(estudiante.get());
        practica.setEmpresa(empresa.get());
        practica.setProfesor(profesor);
        
        practicaService.crearPractica(practica);
        redirectAttributes.addFlashAttribute("success", "Práctica agregada correctamente");
        return "redirect:/profesor/dashboard";
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(
            @PathVariable Long id,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        Profesor profesor = (Profesor) session.getAttribute("usuario");
        if (profesor == null) {
            return "redirect:/";
        }
        
        Optional<Practica> practica = practicaService.obtenerPractica(id);
        if (!practica.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "La práctica no existe");
            return "redirect:/profesor/dashboard";
        }
        
        model.addAttribute("practica", practica.get());
        model.addAttribute("estudiantes", estudianteService.obtenerTodosLosEstudiantes());
        model.addAttribute("empresas", empresaService.obtenerTodasLasEmpresas());
        model.addAttribute("profesores", profesorService.obtenerTodosLosProfesores());
        return "profesor/practicas/editar";
    }
    
    @PostMapping("/actualizar/{id}")
    public String actualizarPractica(
            @PathVariable Long id,
            @ModelAttribute Practica practicaActualizada,
            @RequestParam Long estudianteId,
            @RequestParam Long empresaId,
            @RequestParam Long profesorId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        Profesor profesor = (Profesor) session.getAttribute("usuario");
        if (profesor == null) {
            return "redirect:/";
        }
        
        Optional<Practica> practicaExistente = practicaService.obtenerPractica(id);
        if (!practicaExistente.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "La práctica no existe");
            return "redirect:/profesor/dashboard";
        }
        
        // Verificar que el estudiante existe
        Optional<Estudiante> estudiante = estudianteService.obtenerEstudiante(estudianteId);
        if (!estudiante.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "El estudiante no existe");
            return "redirect:/profesor/practicas/editar/" + id;
        }
        
        // Verificar que la empresa existe
        Optional<Empresa> empresa = empresaService.obtenerEmpresa(empresaId);
        if (!empresa.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "La empresa no existe");
            return "redirect:/profesor/practicas/editar/" + id;
        }
        
        // Verificar que el profesor existe
        Optional<Profesor> profesorTemp = profesorService.obtenerProfesor(profesorId);
        if (!profesorTemp.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "El profesor no existe");
            return "redirect:/profesor/practicas/editar/" + id;
        }
        
        // Validar fechas
        if (practicaActualizada.getFechaInicio().isAfter(practicaActualizada.getFechaFin())) {
            redirectAttributes.addFlashAttribute("error", "La fecha de inicio debe ser anterior a la fecha de fin");
            return "redirect:/profesor/practicas/editar/" + id;
        }
        
        Practica practica = practicaExistente.get();
        practica.setEstudiante(estudiante.get());
        practica.setEmpresa(empresa.get());
        practica.setProfesor(profesorTemp.get());
        practica.setFechaInicio(practicaActualizada.getFechaInicio());
        practica.setFechaFin(practicaActualizada.getFechaFin());
        practica.setActividades(practicaActualizada.getActividades());
        
        practicaService.actualizarPractica(id, practica);
        redirectAttributes.addFlashAttribute("success", "Práctica actualizada correctamente");
        return "redirect:/profesor/dashboard";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminarPractica(
            @PathVariable Long id,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        Profesor profesor = (Profesor) session.getAttribute("usuario");
        if (profesor == null) {
            return "redirect:/";
        }
        
        Optional<Practica> practica = practicaService.obtenerPractica(id);
        if (!practica.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "La práctica no existe");
            return "redirect:/profesor/dashboard";
        }
        
        practicaService.eliminarPractica(id);
        redirectAttributes.addFlashAttribute("success", "Práctica eliminada correctamente");
        return "redirect:/profesor/dashboard";
    }
}
