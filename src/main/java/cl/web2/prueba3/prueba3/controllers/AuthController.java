package cl.web2.prueba3.prueba3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import cl.web2.prueba3.prueba3.models.Estudiante;
import cl.web2.prueba3.prueba3.models.Profesor;
import cl.web2.prueba3.prueba3.services.EstudianteService;
import cl.web2.prueba3.prueba3.services.ProfesorService;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
    
    @Autowired
    private EstudianteService estudianteService;
    
    @Autowired
    private ProfesorService profesorService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String perfil, Model model) {
        if (perfil == null || perfil.isEmpty()) {
            perfil = "estudiante";
        }
        model.addAttribute("perfil", perfil);
        return "login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String email, 
                       @RequestParam String password,
                       @RequestParam(required = false) String perfil,
                       HttpSession session,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        try {
            // Si perfil es estudiante
            if (perfil != null && perfil.equals("estudiante")) {
                Estudiante estudiante = estudianteService.obtenerPorEmail(email);
                if (estudiante != null && estudiante.isActivo() && 
                    passwordEncoder.matches(password, estudiante.getPassword())) {
                    session.setAttribute("usuario", estudiante);
                    session.setAttribute("tipoUsuario", "ESTUDIANTE");
                    session.setMaxInactiveInterval(1800);
                    return "redirect:/estudiante/dashboard";
                }
                model.addAttribute("error", "Email o contraseña incorrectos");
                model.addAttribute("perfil", perfil);
                return "login";
            }
            
            // Si perfil es profesor
            if (perfil != null && perfil.equals("profesor")) {
                Profesor profesor = profesorService.obtenerPorEmail(email);
                if (profesor != null && profesor.isActivo() && 
                    passwordEncoder.matches(password, profesor.getPassword())) {
                    session.setAttribute("usuario", profesor);
                    session.setAttribute("tipoUsuario", "PROFESOR");
                    session.setMaxInactiveInterval(1800);
                    return "redirect:/profesor/dashboard";
                }
                model.addAttribute("error", "Email o contraseña incorrectos");
                model.addAttribute("perfil", perfil);
                return "login";
            }
            
            // Si no hay perfil especificado, intentar ambos
            Estudiante estudiante = estudianteService.obtenerPorEmail(email);
            if (estudiante != null && estudiante.isActivo() && 
                passwordEncoder.matches(password, estudiante.getPassword())) {
                session.setAttribute("usuario", estudiante);
                session.setAttribute("tipoUsuario", "ESTUDIANTE");
                session.setMaxInactiveInterval(1800);
                return "redirect:/estudiante/dashboard";
            }
            
            Profesor profesor = profesorService.obtenerPorEmail(email);
            if (profesor != null && profesor.isActivo() && 
                passwordEncoder.matches(password, profesor.getPassword())) {
                session.setAttribute("usuario", profesor);
                session.setAttribute("tipoUsuario", "PROFESOR");
                session.setMaxInactiveInterval(1800);
                return "redirect:/profesor/dashboard";
            }
            
            redirectAttributes.addFlashAttribute("error", "Email o contraseña incorrectos");
            return "redirect:/login";
            
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error en la autenticación");
            return "redirect:/login";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}



