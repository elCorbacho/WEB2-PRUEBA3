package cl.web2.prueba3.prueba3.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import cl.web2.prueba3.prueba3.models.Profesor;
import cl.web2.prueba3.prueba3.services.PracticaService;

@Controller
@RequestMapping("/profesor")
@Transactional(readOnly = true)
public class ProfesorDashboardController {
    
    @Autowired
    private PracticaService practicaService;
    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Profesor profesor = (Profesor) session.getAttribute("usuario");
        
        if (profesor == null) {
            System.out.println("No hay usuario en sesión, redirigiendo a /");
            return "redirect:/";
        }
        
        System.out.println("Acceso a dashboard de profesor: " + profesor.getNombres());
        model.addAttribute("profesor", profesor);
        model.addAttribute("practicas", practicaService.obtenerPracticasPorProfesor(profesor.getId()));
        model.addAttribute("todasLasPracticas", practicaService.obtenerTodasLasPracticas());
        return "profesor/dashboard";
    }
}
