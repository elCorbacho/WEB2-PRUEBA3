package cl.web2.prueba3.prueba3.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import cl.web2.prueba3.prueba3.models.Estudiante;
import cl.web2.prueba3.prueba3.services.EstudianteService;
import cl.web2.prueba3.prueba3.services.CarreraService;
import java.util.Optional;

@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {
    
    @Autowired
    private EstudianteService estudianteService;
    
    @Autowired
    private CarreraService carreraService;
    
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("estudiantes", estudianteService.obtenerTodosLosEstudiantes());
        return "estudiante/lista";
    }
    
    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        model.addAttribute("carreras", carreraService.obtenerTodasLasCarreras());
        return "estudiante/crear";
    }
    
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Estudiante estudiante) {
        estudianteService.crearEstudiante(estudiante);
        return "redirect:/estudiantes";
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Optional<Estudiante> estudiante = estudianteService.obtenerEstudiante(id);
        if (estudiante.isPresent()) {
            model.addAttribute("estudiante", estudiante.get());
            model.addAttribute("carreras", carreraService.obtenerTodasLasCarreras());
            return "estudiante/editar";
        }
        return "redirect:/estudiantes";
    }
    
    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute Estudiante estudiante) {
        estudianteService.actualizarEstudiante(id, estudiante);
        return "redirect:/estudiantes";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        estudianteService.eliminarEstudiante(id);
        return "redirect:/estudiantes";
    }
}
