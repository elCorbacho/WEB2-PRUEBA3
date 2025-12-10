package cl.web2.prueba3.prueba3.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.web2.prueba3.prueba3.models.Empresa;
import cl.web2.prueba3.prueba3.services.EmpresaService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaApiController {
    
    @Autowired
    private EmpresaService empresaService;
    
    @PostMapping
    public ResponseEntity<Empresa> crearEmpresa(@RequestBody Empresa empresa) {
        Empresa nuevaEmpresa = empresaService.crearEmpresa(empresa);
        return new ResponseEntity<>(nuevaEmpresa, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<Empresa>> obtenerTodas() {
        List<Empresa> empresas = empresaService.obtenerTodasLasEmpresas();
        return new ResponseEntity<>(empresas, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Empresa> obtenerPorId(@PathVariable Long id) {
        Optional<Empresa> empresa = empresaService.obtenerEmpresa(id);
        if (empresa.isPresent()) {
            return new ResponseEntity<>(empresa.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Empresa> actualizarEmpresa(@PathVariable Long id, @RequestBody Empresa empresaActualizada) {
        Empresa empresa = empresaService.actualizarEmpresa(id, empresaActualizada);
        if (empresa != null) {
            return new ResponseEntity<>(empresa, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpresa(@PathVariable Long id) {
        if (empresaService.eliminarEmpresa(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<Empresa> obtenerPorEmail(@PathVariable String email) {
        Empresa empresa = empresaService.obtenerEmpresaPorEmail(email);
        if (empresa != null) {
            return new ResponseEntity<>(empresa, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/jefe/{jefeId}")
    public ResponseEntity<List<Empresa>> obtenerPorJefe(@PathVariable Long jefeId) {
        List<Empresa> empresas = empresaService.obtenerEmpresasPorJefe(jefeId);
        return new ResponseEntity<>(empresas, HttpStatus.OK);
    }
}
