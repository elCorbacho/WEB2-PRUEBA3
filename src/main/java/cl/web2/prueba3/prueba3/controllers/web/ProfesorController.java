package cl.web2.prueba3.prueba3.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;
import cl.web2.prueba3.prueba3.models.Profesor;
import cl.web2.prueba3.prueba3.services.ProfesorService;
import java.util.Optional;

@Controller
@RequestMapping("/profesores")
public class ProfesorController {
    
    @Autowired
    private ProfesorService profesorService;
    
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("profesores", profesorService.obtenerTodosLosProfesores());
        return "profesor/lista";
    }
    
    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("profesor", new Profesor());
        return "profesor/crear";
    }
    
    @PostMapping("/guardar")
    public String guardar(@NonNull @ModelAttribute Profesor profesor) {
        profesorService.crearProfesor(profesor);
        return "redirect:/profesores";
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@NonNull @PathVariable Long id, Model model) {
        Optional<Profesor> profesor = profesorService.obtenerProfesor(id);
        if (profesor.isPresent()) {
            model.addAttribute("profesor", profesor.get());
            return "profesor/editar";
        }
        return "redirect:/profesores";
    }
    
    @PostMapping("/actualizar/{id}")
    public String actualizar(@NonNull @PathVariable Long id, @NonNull @ModelAttribute Profesor profesor) {
        profesorService.actualizarProfesor(id, profesor);
        return "redirect:/profesores";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@NonNull @PathVariable Long id) {
        profesorService.eliminarProfesor(id);
        return "redirect:/profesores";
    }
}
