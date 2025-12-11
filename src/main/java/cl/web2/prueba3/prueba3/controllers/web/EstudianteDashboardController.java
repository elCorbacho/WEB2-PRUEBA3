package cl.web2.prueba3.prueba3.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import cl.web2.prueba3.prueba3.models.Estudiante;
import cl.web2.prueba3.prueba3.services.EstudianteService;
import cl.web2.prueba3.prueba3.services.PracticaService;

@Controller
@RequestMapping("/estudiante")
public class EstudianteDashboardController {
    
    @Autowired
    private EstudianteService estudianteService;
    
    @Autowired
    private PracticaService practicaService;
    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Estudiante estudiante = (Estudiante) session.getAttribute("usuario");
        
        if (estudiante == null) {
            System.out.println("No hay usuario en sesión, redirigiendo a /");
            return "redirect:/";
        }
        
        System.out.println("Acceso a dashboard de estudiante: " + estudiante.getNombre());
        Long estudianteId = estudiante.getId();
        model.addAttribute("estudiante", estudiante);
        model.addAttribute("practicas", practicaService.obtenerPracticasPorEstudiante(estudianteId));
        model.addAttribute("tienePracticaEnCurso", practicaService.tienePracticaEnCurso(estudianteId));
        return "estudiante/dashboard";
    }
}

