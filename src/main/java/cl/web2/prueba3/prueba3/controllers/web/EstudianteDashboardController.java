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
@RequestMapping("/estudiante")
public class EstudianteDashboardController {
    
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
        if (usuario.isPresent() && usuario.get().getEstudiante() != null) {
            model.addAttribute("estudiante", usuario.get().getEstudiante());
            model.addAttribute("practicas", practicaService.obtenerPracticasPorEstudiante(usuario.get().getEstudiante().getId()));
            return "estudiante/dashboard";
        }
        
        return "redirect:/logout";
    }
}
