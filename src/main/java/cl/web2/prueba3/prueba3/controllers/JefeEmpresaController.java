package cl.web2.prueba3.prueba3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.web2.prueba3.prueba3.models.Jefe_empresa;
import cl.web2.prueba3.prueba3.services.JefeEmpresaService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/jefes")
public class JefeEmpresaController {
    
    @Autowired
    private JefeEmpresaService jefeEmpresaService;
    
    @PostMapping
    public ResponseEntity<Jefe_empresa> crearJefe(@RequestBody Jefe_empresa jefe) {
        Jefe_empresa nuevoJefe = jefeEmpresaService.crearJefeEmpresa(jefe);
        return new ResponseEntity<>(nuevoJefe, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<Jefe_empresa>> obtenerTodos() {
        List<Jefe_empresa> jefes = jefeEmpresaService.obtenerTodosLosJefes();
        return new ResponseEntity<>(jefes, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Jefe_empresa> obtenerPorId(@PathVariable Long id) {
        Optional<Jefe_empresa> jefe = jefeEmpresaService.obtenerJefeEmpresa(id);
        if (jefe.isPresent()) {
            return new ResponseEntity<>(jefe.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Jefe_empresa> actualizarJefe(@PathVariable Long id, @RequestBody Jefe_empresa jefeActualizado) {
        Jefe_empresa jefe = jefeEmpresaService.actualizarJefeEmpresa(id, jefeActualizado);
        if (jefe != null) {
            return new ResponseEntity<>(jefe, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarJefe(@PathVariable Long id) {
        if (jefeEmpresaService.eliminarJefeEmpresa(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/mail/{mail}")
    public ResponseEntity<Jefe_empresa> obtenerPorMail(@PathVariable String mail) {
        Jefe_empresa jefe = jefeEmpresaService.obtenerJefePorMail(mail);
        if (jefe != null) {
            return new ResponseEntity<>(jefe, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
