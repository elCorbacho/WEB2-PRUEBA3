package cl.web2.prueba3.prueba3.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Componente que muestra información detallada cuando la aplicación inicia correctamente.
 * Incluye URLs de acceso, perfil activo, base de datos, etc.
 */
@Slf4j
@Component
public class ApplicationStartupLogger {
    
    private final Environment env;
    
    public ApplicationStartupLogger(Environment env) {
        this.env = env;
    }
    
    @EventListener(ApplicationReadyEvent.class)
    public void logApplicationStartup() {
        try {
            String protocol = "http";
            String serverPort = env.getProperty("server.port", "8080");
            String contextPath = env.getProperty("server.servlet.context-path", "/");
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            String appName = env.getProperty("spring.application.name", "Prueba3");
            String profiles = env.getActiveProfiles().length == 0 
                ? "default" 
                : String.join(", ", env.getActiveProfiles());
            String database = env.getProperty("spring.datasource.url", "H2 (en memoria)");
            
            log.info("\n" +
                    "----------------------------------------------------------\n\t" +
                    "[OK] Aplicacion '{}' iniciada correctamente!\n\t" +
                    "[>>] Local:      {}://localhost:{}{}\n\t" +
                    "[>>] Network:    {}://{}:{}{}\n\t" +
                    "[#]  Profile(s): {}\n\t" +
                    "[DB] Base de datos: {}\n\t" +
                    "[API] API Docs:   {}://localhost:{}/swagger-ui.html\n" +
                    "----------------------------------------------------------",
                    appName,
                    protocol,
                    serverPort,
                    contextPath,
                    protocol,
                    hostAddress,
                    serverPort,
                    contextPath,
                    profiles,
                    database,
                    protocol,
                    serverPort
            );
        } catch (UnknownHostException e) {
            log.warn("No se pudo determinar la dirección IP del host", e);
        }
    }
}
