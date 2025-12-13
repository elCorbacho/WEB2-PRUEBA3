package cl.web2.prueba3.prueba3.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;
import cl.web2.prueba3.prueba3.models.Empresa;
import cl.web2.prueba3.prueba3.services.EmpresaService;
import cl.web2.prueba3.prueba3.services.JefeEmpresaService;

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
    public String guardar(@NonNull @ModelAttribute Empresa empresa) {
        if (empresa != null) {
            empresaService.crearEmpresa(empresa);
        }
        return "redirect:/empresas";
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        try {
            Empresa empresa = empresaService.obtenerEmpresa(id);
            model.addAttribute("empresa", empresa);
            model.addAttribute("jefes", jefeEmpresaService.obtenerTodosLosJefes());
            return "empresa/editar";
        } catch (Exception e) {
            return "redirect:/empresas";
        }
    }
    
    @PostMapping("/actualizar/{id}")
    public String actualizar(@NonNull @PathVariable Long id, @NonNull @ModelAttribute Empresa empresa) {
        if (id != null && empresa != null) {
            empresaService.actualizarEmpresa(id, empresa);
        }
        return "redirect:/empresas";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@NonNull @PathVariable Long id) {
        if (id != null) {
            empresaService.eliminarEmpresa(id);
        }
        return "redirect:/empresas";
    }
}
