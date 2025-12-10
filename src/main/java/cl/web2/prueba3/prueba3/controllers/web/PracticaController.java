package cl.web2.prueba3.prueba3.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import cl.web2.prueba3.prueba3.models.Practica;
import cl.web2.prueba3.prueba3.services.PracticaService;
import cl.web2.prueba3.prueba3.services.EstudianteService;
import cl.web2.prueba3.prueba3.services.EmpresaService;
import cl.web2.prueba3.prueba3.services.ProfesorService;
import java.util.Optional;

@Controller
@RequestMapping("/practicas")
public class PracticaController {
    
    @Autowired
    private PracticaService practicaService;
    
    @Autowired
    private EstudianteService estudianteService;
    
    @Autowired
    private EmpresaService empresaService;
    
    @Autowired
    private ProfesorService profesorService;
    
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("practicas", practicaService.obtenerTodasLasPracticas());
        return "practica/lista";
    }
    
    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("practica", new Practica());
        model.addAttribute("estudiantes", estudianteService.obtenerTodosLosEstudiantes());
        model.addAttribute("empresas", empresaService.obtenerTodasLasEmpresas());
        model.addAttribute("profesores", profesorService.obtenerTodosLosProfesores());
        return "practica/crear";
    }
    
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Practica practica) {
        practicaService.crearPractica(practica);
        return "redirect:/practicas";
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Optional<Practica> practica = practicaService.obtenerPractica(id);
        if (practica.isPresent()) {
            model.addAttribute("practica", practica.get());
            model.addAttribute("estudiantes", estudianteService.obtenerTodosLosEstudiantes());
            model.addAttribute("empresas", empresaService.obtenerTodasLasEmpresas());
            model.addAttribute("profesores", profesorService.obtenerTodosLosProfesores());
            return "practica/editar";
        }
        return "redirect:/practicas";
    }
    
    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute Practica practica) {
        practicaService.actualizarPractica(id, practica);
        return "redirect:/practicas";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        practicaService.eliminarPractica(id);
        return "redirect:/practicas";
    }
}
