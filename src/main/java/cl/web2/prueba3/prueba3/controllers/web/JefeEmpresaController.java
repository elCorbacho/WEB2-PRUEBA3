package cl.web2.prueba3.prueba3.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import cl.web2.prueba3.prueba3.models.Jefe_empresa;
import cl.web2.prueba3.prueba3.services.JefeEmpresaService;
import java.util.Optional;

@Controller
@RequestMapping("/jefes")
public class JefeEmpresaController {
    
    @Autowired
    private JefeEmpresaService jefeEmpresaService;
    
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("jefes", jefeEmpresaService.obtenerTodosLosJefes());
        return "jefe/lista";
    }
    
    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("jefe", new Jefe_empresa());
        return "jefe/crear";
    }
    
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Jefe_empresa jefe) {
        jefeEmpresaService.crearJefeEmpresa(jefe);
        return "redirect:/jefes";
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Optional<Jefe_empresa> jefe = jefeEmpresaService.obtenerJefeEmpresa(id);
        if (jefe.isPresent()) {
            model.addAttribute("jefe", jefe.get());
            return "jefe/editar";
        }
        return "redirect:/jefes";
    }
    
    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute Jefe_empresa jefe) {
        jefeEmpresaService.actualizarJefeEmpresa(id, jefe);
        return "redirect:/jefes";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        jefeEmpresaService.eliminarJefeEmpresa(id);
        return "redirect:/jefes";
    }
}
