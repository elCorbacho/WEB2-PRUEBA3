package cl.web2.prueba3.prueba3.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import cl.web2.prueba3.prueba3.models.*;
import cl.web2.prueba3.prueba3.services.*;
import java.time.LocalDate;

@Configuration
public class DataLoader {
    
    @Bean
    public CommandLineRunner loadData(
            CarreraService carreraService,
            EstudianteService estudianteService,
            ProfesorService profesorService,
            TipoUsuarioService tipoUsuarioService,
            JefeEmpresaService jefeEmpresaService,
            EmpresaService empresaService,
            PracticaService practicaService,
            PasswordEncoder passwordEncoder) {
        
        return args -> {
            // ============================================================
            // 0. Crear Tipos de Usuario
            // ============================================================
            TipoUsuario tipoEstudiante = new TipoUsuario();
            tipoEstudiante.setTipo("ESTUDIANTE");
            tipoEstudiante = tipoUsuarioService.crear(tipoEstudiante);
            
            TipoUsuario tipoProfesor = new TipoUsuario();
            tipoProfesor.setTipo("PROFESOR");
            tipoProfesor = tipoUsuarioService.crear(tipoProfesor);
            
            // ============================================================
            // 1. Crear Carreras
            // ============================================================
            Carrera carrera1 = new Carrera();
            carrera1.setNombreCarrera("Ingeniería en Informática");
            carrera1 = carreraService.crearCarrera(carrera1);
            
            Carrera carrera2 = new Carrera();
            carrera2.setNombreCarrera("Ingeniería en Sistemas");
            carrera2 = carreraService.crearCarrera(carrera2);
            
            Carrera carrera3 = new Carrera();
            carrera3.setNombreCarrera("Administración de Empresas");
            carrera3 = carreraService.crearCarrera(carrera3);
            
            // ============================================================
            // 2. Crear Profesores
            // ============================================================
            Profesor profesor1 = new Profesor();
            profesor1.setNombres("Juan");
            profesor1.setApellidos("García López");
            profesor1.setEmail("juan.garcia@universidad.cl");
            profesor1.setPassword(passwordEncoder.encode("password123"));
            profesor1.setCorreoElectronico("juan.garcia@universidad.cl");
            profesor1.setTipoUsuario(tipoProfesor);
            profesor1.setActivo(true);
            profesor1 = profesorService.crearProfesor(profesor1);
            
            Profesor profesor2 = new Profesor();
            profesor2.setNombres("María");
            profesor2.setApellidos("Rodríguez Martínez");
            profesor2.setEmail("maria.rodriguez@universidad.cl");
            profesor2.setPassword(passwordEncoder.encode("password123"));
            profesor2.setCorreoElectronico("maria.rodriguez@universidad.cl");
            profesor2.setTipoUsuario(tipoProfesor);
            profesor2.setActivo(true);
            profesor2 = profesorService.crearProfesor(profesor2);
            
            Profesor profesor3 = new Profesor();
            profesor3.setNombres("Carlos");
            profesor3.setApellidos("Fernández Silva");
            profesor3.setEmail("carlos.fernandez@universidad.cl");
            profesor3.setPassword(passwordEncoder.encode("password123"));
            profesor3.setCorreoElectronico("carlos.fernandez@universidad.cl");
            profesor3.setTipoUsuario(tipoProfesor);
            profesor3.setActivo(true);
            profesor3 = profesorService.crearProfesor(profesor3);
            
            // ============================================================
            // 3. Crear Jefes de Empresa
            // ============================================================
            Jefe_empresa jefe1 = new Jefe_empresa();
            jefe1.setNombre("Pedro");
            jefe1.setApellidos("González Hernández");
            jefe1.setMail("pedro.gonzalez@empresa1.cl");
            jefe1 = jefeEmpresaService.crearJefeEmpresa(jefe1);
            
            Jefe_empresa jefe2 = new Jefe_empresa();
            jefe2.setNombre("Ana");
            jefe2.setApellidos("López Martínez");
            jefe2.setMail("ana.lopez@empresa2.cl");
            jefe2 = jefeEmpresaService.crearJefeEmpresa(jefe2);
            
            Jefe_empresa jefe3 = new Jefe_empresa();
            jefe3.setNombre("Roberto");
            jefe3.setApellidos("Sánchez Torres");
            jefe3.setMail("roberto.sanchez@empresa3.cl");
            jefe3 = jefeEmpresaService.crearJefeEmpresa(jefe3);
            
            // ============================================================
            // 4. Crear Empresas
            // ============================================================
            Empresa empresa1 = new Empresa();
            empresa1.setNombre("TechSoft Solutions");
            empresa1.setDireccion("Avenida Principal 123, Santiago");
            empresa1.setTelefono("+56223334444");
            empresa1.setEmail("info@techsoft.cl");
            empresa1.setJefe(jefe1);
            empresa1 = empresaService.crearEmpresa(empresa1);
            
            Empresa empresa2 = new Empresa();
            empresa2.setNombre("Innovatech Chile");
            empresa2.setDireccion("Calle Secundaria 456, Valparaíso");
            empresa2.setTelefono("+56322225555");
            empresa2.setEmail("contacto@innovatech.cl");
            empresa2.setJefe(jefe2);
            empresa2 = empresaService.crearEmpresa(empresa2);
            
            Empresa empresa3 = new Empresa();
            empresa3.setNombre("Digital Consulting Group");
            empresa3.setDireccion("Boulevard Central 789, Concepción");
            empresa3.setTelefono("+56412226666");
            empresa3.setEmail("hello@digitalconsulting.cl");
            empresa3.setJefe(jefe3);
            empresa3 = empresaService.crearEmpresa(empresa3);
            
            // ============================================================
            // 5. Crear Estudiantes
            // ============================================================
            Estudiante estudiante1 = new Estudiante();
            estudiante1.setNombre("Luis");
            estudiante1.setApellido("Morales Castillo");
            estudiante1.setEmail("luis.morales@estudiante.cl");
            estudiante1.setPassword(passwordEncoder.encode("password123"));
            estudiante1.setCarrera(carrera1);
            estudiante1.setTipoUsuario(tipoEstudiante);
            estudiante1.setActivo(true);
            estudiante1 = estudianteService.crearEstudiante(estudiante1);
            
            Estudiante estudiante2 = new Estudiante();
            estudiante2.setNombre("Francisca");
            estudiante2.setApellido("Díaz Gutiérrez");
            estudiante2.setEmail("francisca.diaz@estudiante.cl");
            estudiante2.setPassword(passwordEncoder.encode("password123"));
            estudiante2.setCarrera(carrera2);
            estudiante2.setTipoUsuario(tipoEstudiante);
            estudiante2.setActivo(true);
            estudiante2 = estudianteService.crearEstudiante(estudiante2);
            
            Estudiante estudiante3 = new Estudiante();
            estudiante3.setNombre("Diego");
            estudiante3.setApellido("Vargas Riquelme");
            estudiante3.setEmail("diego.vargas@estudiante.cl");
            estudiante3.setPassword(passwordEncoder.encode("password123"));
            estudiante3.setCarrera(carrera3);
            estudiante3.setTipoUsuario(tipoEstudiante);
            estudiante3.setActivo(true);
            estudiante3 = estudianteService.crearEstudiante(estudiante3);

            Estudiante estudiante4 = new Estudiante();
            estudiante4.setNombre("Ulises");
            estudiante4.setApellido("Contreras Perez");
            estudiante4.setEmail("ulises.contreras@estudiante.cl");
            estudiante4.setPassword(passwordEncoder.encode("password123"));
            estudiante4.setCarrera(carrera3);
            estudiante4.setTipoUsuario(tipoEstudiante);
            estudiante4.setActivo(true);
            estudiante4 = estudianteService.crearEstudiante(estudiante4);
            
            
            // ============================================================
            // 6. Crear Prácticas Profesionales
            // ============================================================
            Practica practica1 = new Practica();
            practica1.setEstudiante(estudiante1);
            practica1.setEmpresa(empresa1);
            practica1.setFechaInicio(LocalDate.of(2025, 1, 15));
            practica1.setFechaFin(LocalDate.of(2025, 3, 15));
            practica1.setActividades("Desarrollo de módulo de autenticación, pruebas unitarias, documentación de código");
            practica1.setProfesor(profesor1);
            practicaService.crearPractica(practica1);
            
            Practica practica2 = new Practica();
            practica2.setEstudiante(estudiante2);
            practica2.setEmpresa(empresa2);
            practica2.setFechaInicio(LocalDate.of(2025, 2, 1));
            practica2.setFechaFin(LocalDate.of(2026, 4, 1));
            practica2.setActividades("Análisis de requisitos, diseño de base de datos, implementación de API REST");
            practica2.setProfesor(profesor2);
            practicaService.crearPractica(practica2);
            
            Practica practica3 = new Practica();
            practica3.setEstudiante(estudiante3);
            practica3.setEmpresa(empresa3);
            practica3.setFechaInicio(LocalDate.of(2025, 1, 20));
            practica3.setFechaFin(LocalDate.of(2026, 3, 20));
            practica3.setActividades("Consultoría en gestión de proyectos, análisis de procesos, mejora de eficiencia operacional");
            practica3.setProfesor(profesor3);
            practicaService.crearPractica(practica3);
            
            System.out.println("✅ Base de datos cargada correctamente");
        };
    }
}