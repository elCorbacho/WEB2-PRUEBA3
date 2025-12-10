package cl.web2.prueba3.prueba3.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import cl.web2.prueba3.prueba3.models.Carrera;
import cl.web2.prueba3.prueba3.services.CarreraService;
import java.util.Optional;

@Controller
@RequestMapping("/carreras")
public class CarreraController {
    
    @Autowired
    private CarreraService carreraService;
    
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("carreras", carreraService.obtenerTodasLasCarreras());
        return "carrera/lista";
    }
    
    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("carrera", new Carrera());
        return "carrera/crear";
    }
    
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Carrera carrera) {
        carreraService.crearCarrera(carrera);
        return "redirect:/carreras";
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Optional<Carrera> carrera = carreraService.obtenerCarrera(id);
        if (carrera.isPresent()) {
            model.addAttribute("carrera", carrera.get());
            return "carrera/editar";
        }
        return "redirect:/carreras";
    }
    
    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute Carrera carrera) {
        carreraService.actualizarCarrera(id, carrera);
        return "redirect:/carreras";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        carreraService.eliminarCarrera(id);
        return "redirect:/carreras";
    }
}
