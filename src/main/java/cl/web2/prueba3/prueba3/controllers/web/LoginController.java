package cl.web2.prueba3.prueba3.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import cl.web2.prueba3.prueba3.models.Usuario;
import cl.web2.prueba3.prueba3.models.Rol;
import cl.web2.prueba3.prueba3.services.UsuarioService;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class LoginController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping
    public String home() {
        return "index";
    }
    
    @GetMapping("/seleccionar-perfil")
    public String seleccionarPerfil(@RequestParam(required = false) String perfil, Model model) {
        if (perfil != null) {
            model.addAttribute("perfil", perfil);
            model.addAttribute("rolSeleccionado", perfil.equals("estudiante") ? Rol.ESTUDIANTE : Rol.PROFESOR);
            return "login";
        }
        return "seleccionar-perfil";
    }
    
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String email, 
                                @RequestParam String password,
                                @RequestParam String perfil,
                                HttpSession session,
                                Model model) {
        if (usuarioService.validarCredenciales(email, password, perfil)) {
            Optional<Usuario> usuario = usuarioService.buscarPorEmail(email);
            if (usuario.isPresent() && usuario.get().getRol().name().equals(perfil.toUpperCase())) {
                session.setAttribute("usuarioId", usuario.get().getId());
                session.setAttribute("usuarioEmail", usuario.get().getEmail());
                session.setAttribute("rol", usuario.get().getRol());
                
                // Redirigir según rol
                switch (usuario.get().getRol()) {
                    case ESTUDIANTE:
                        return "redirect:/estudiante/dashboard";
                    case PROFESOR:
                        return "redirect:/profesor/dashboard";
                    default:
                        return "redirect:/";
                }
            }
        }
        model.addAttribute("error", "Email o contraseña inválidos para " + perfil);
        model.addAttribute("perfil", perfil);
        return "login";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
