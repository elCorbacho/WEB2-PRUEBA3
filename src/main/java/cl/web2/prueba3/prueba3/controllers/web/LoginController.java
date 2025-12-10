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
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorEmail(email);
        
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            
            if (!usuario.getPassword().equals(password)) {
                model.addAttribute("error", "Contraseña incorrecta");
                model.addAttribute("perfil", perfil);
                return "login";
            }
            
            Rol rolEsperado = perfil.equalsIgnoreCase("estudiante") ? Rol.ESTUDIANTE : Rol.PROFESOR;
            if (usuario.getRol() != rolEsperado) {
                model.addAttribute("error", "Este usuario no es " + perfil);
                model.addAttribute("perfil", perfil);
                return "login";
            }
            
            if (!usuario.isActivo()) {
                model.addAttribute("error", "Usuario inactivo");
                model.addAttribute("perfil", perfil);
                return "login";
            }
            
            session.setAttribute("usuarioId", usuario.getId());
            session.setAttribute("usuarioEmail", usuario.getEmail());
            session.setAttribute("rol", usuario.getRol());
            session.setAttribute("perfil", perfil);
            
            if (usuario.getEstudiante() != null) {
                session.setAttribute("estudianteId", usuario.getEstudiante().getId());
            }
            if (usuario.getProfesor() != null) {
                session.setAttribute("profesorId", usuario.getProfesor().getId());
            }
            
            switch (usuario.getRol()) {
                case ESTUDIANTE:
                    return "redirect:/estudiante/dashboard";
                case PROFESOR:
                    return "redirect:/profesor/dashboard";
                default:
                    return "redirect:/";
            }
        }
        
        model.addAttribute("error", "Email no encontrado");
        model.addAttribute("perfil", perfil);
        return "login";
    }
    
    @GetMapping("/logout")
    public String logoutGet(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
