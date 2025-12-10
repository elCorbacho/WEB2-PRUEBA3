package cl.web2.prueba3.prueba3.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
            JefeEmpresaService jefeEmpresaService,
            EmpresaService empresaService,
            PracticaService practicaService,
            UsuarioService usuarioService) {
        
        return args -> {
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
            profesor1.setCorreoElectronico("juan.garcia@universidad.cl");
            profesor1 = profesorService.crearProfesor(profesor1);
            
            Profesor profesor2 = new Profesor();
            profesor2.setNombres("María");
            profesor2.setApellidos("Rodríguez Martínez");
            profesor2.setCorreoElectronico("maria.rodriguez@universidad.cl");
            profesor2 = profesorService.crearProfesor(profesor2);
            
            Profesor profesor3 = new Profesor();
            profesor3.setNombres("Carlos");
            profesor3.setApellidos("Fernández Silva");
            profesor3.setCorreoElectronico("carlos.fernandez@universidad.cl");
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
            estudiante1.setCarrera(carrera1);
            estudiante1 = estudianteService.crearEstudiante(estudiante1);
            
            Estudiante estudiante2 = new Estudiante();
            estudiante2.setNombre("Francisca");
            estudiante2.setApellido("Díaz Gutiérrez");
            estudiante2.setEmail("francisca.diaz@estudiante.cl");
            estudiante2.setCarrera(carrera2);
            estudiante2 = estudianteService.crearEstudiante(estudiante2);
            
            Estudiante estudiante3 = new Estudiante();
            estudiante3.setNombre("Diego");
            estudiante3.setApellido("Vargas Riquelme");
            estudiante3.setEmail("diego.vargas@estudiante.cl");
            estudiante3.setCarrera(carrera3);
            estudiante3 = estudianteService.crearEstudiante(estudiante3);

            Estudiante estudiante4 = new Estudiante();
            estudiante4.setNombre("Ulises");
            estudiante4.setApellido("Contreras Perez");
            estudiante4.setEmail("ulises.contreras@estudiante.cl");
            estudiante4.setCarrera(carrera3);
            estudiante4 = estudianteService.crearEstudiante(estudiante4);
            
            // ============================================================
            // 6. Crear Usuarios
            // ============================================================
            Usuario usuarioEstudiante1 = new Usuario();
            usuarioEstudiante1.setEmail("luis.morales@estudiante.cl");
            usuarioEstudiante1.setPassword("password123");
            usuarioEstudiante1.setRol(Rol.ESTUDIANTE);
            usuarioEstudiante1.setActivo(true);
            usuarioEstudiante1.setEstudiante(estudiante1);
            usuarioService.crearUsuario(usuarioEstudiante1);
            
            Usuario usuarioProfesor1 = new Usuario();
            usuarioProfesor1.setEmail("juan.garcia@universidad.cl");
            usuarioProfesor1.setPassword("password123");
            usuarioProfesor1.setRol(Rol.PROFESOR);
            usuarioProfesor1.setActivo(true);
            usuarioProfesor1.setProfesor(profesor1);
            usuarioService.crearUsuario(usuarioProfesor1);
            
            Usuario usuarioEstudiante2 = new Usuario();
            usuarioEstudiante2.setEmail("francisca.diaz@estudiante.cl");
            usuarioEstudiante2.setPassword("password123");
            usuarioEstudiante2.setRol(Rol.ESTUDIANTE);
            usuarioEstudiante2.setActivo(true);
            usuarioEstudiante2.setEstudiante(estudiante2);
            usuarioService.crearUsuario(usuarioEstudiante2);
            
            Usuario usuarioProfesor2 = new Usuario();
            usuarioProfesor2.setEmail("maria.rodriguez@universidad.cl");
            usuarioProfesor2.setPassword("password123");
            usuarioProfesor2.setRol(Rol.PROFESOR);
            usuarioProfesor2.setActivo(true);
            usuarioProfesor2.setProfesor(profesor2);
            usuarioService.crearUsuario(usuarioProfesor2);
            
            Usuario usuarioEstudiante3 = new Usuario();
            usuarioEstudiante3.setEmail("diego.vargas@estudiante.cl");
            usuarioEstudiante3.setPassword("password123");
            usuarioEstudiante3.setRol(Rol.ESTUDIANTE);
            usuarioEstudiante3.setActivo(true);
            usuarioEstudiante3.setEstudiante(estudiante3);
            usuarioService.crearUsuario(usuarioEstudiante3);
            
            Usuario usuarioProfesor3 = new Usuario();
            usuarioProfesor3.setEmail("carlos.fernandez@universidad.cl");
            usuarioProfesor3.setPassword("password123");
            usuarioProfesor3.setRol(Rol.PROFESOR);
            usuarioProfesor3.setActivo(true);
            usuarioProfesor3.setProfesor(profesor3);
            usuarioService.crearUsuario(usuarioProfesor3);

            Usuario usuarioEstudiante4 = new Usuario();
            usuarioEstudiante4.setEmail("ulises.contreras@estudiante.cl");
            usuarioEstudiante4.setPassword("password123");
            usuarioEstudiante4.setRol(Rol.ESTUDIANTE);
            usuarioEstudiante4.setActivo(true);
            usuarioEstudiante4.setEstudiante(estudiante4);
            usuarioService.crearUsuario(usuarioEstudiante4);
            
            // ============================================================
            // 7. Crear Prácticas Profesionales
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
            
            System.out.println("✅ Base de datos cargada correctamente con 3 registros en cada tabla");
        };
    }
}