package cl.web2.prueba3.prueba3.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import cl.web2.prueba3.prueba3.models.Empresa;
import cl.web2.prueba3.prueba3.services.EmpresaService;
import cl.web2.prueba3.prueba3.services.JefeEmpresaService;
import java.util.Optional;

@Controller
@RequestMapping("/empresas")
public class EmpresaController {
    
    @Autowired
    private EmpresaService empresaService;
    
    @Autowired
    private JefeEmpresaService jefeEmpresaService;
    
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("empresas", empresaService.obtenerTodasLasEmpresas());
        return "empresa/lista";
    }
    
    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("empresa", new Empresa());
        model.addAttribute("jefes", jefeEmpresaService.obtenerTodosLosJefes());
        return "empresa/crear";
    }
    
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Empresa empresa) {
        empresaService.crearEmpresa(empresa);
        return "redirect:/empresas";
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Optional<Empresa> empresa = empresaService.obtenerEmpresa(id);
        if (empresa.isPresent()) {
            model.addAttribute("empresa", empresa.get());
            model.addAttribute("jefes", jefeEmpresaService.obtenerTodosLosJefes());
            return "empresa/editar";
        }
        return "redirect:/empresas";
    }
    
    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute Empresa empresa) {
        empresaService.actualizarEmpresa(id, empresa);
        return "redirect:/empresas";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        empresaService.eliminarEmpresa(id);
        return "redirect:/empresas";
    }
}
