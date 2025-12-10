package cl.web2.prueba3.prueba3.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import cl.web2.prueba3.prueba3.models.Usuario;
import cl.web2.prueba3.prueba3.services.UsuarioService;
import cl.web2.prueba3.prueba3.services.PracticaService;
import java.util.Optional;

@Controller
@RequestMapping("/profesor")
public class ProfesorDashboardController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private PracticaService practicaService;
    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Long usuarioId = (Long) session.getAttribute("usuarioId");
        
        if (usuarioId == null) {
            return "redirect:/";
        }
        
        Optional<Usuario> usuario = usuarioService.obtenerUsuario(usuarioId);
        if (usuario.isPresent() && usuario.get().getProfesor() != null) {
            model.addAttribute("profesor", usuario.get().getProfesor());
            model.addAttribute("practicas", practicaService.obtenerPracticasPorProfesor(usuario.get().getProfesor().getId()));
            return "profesor/dashboard";
        }
        
        return "redirect:/logout";
    }
}
