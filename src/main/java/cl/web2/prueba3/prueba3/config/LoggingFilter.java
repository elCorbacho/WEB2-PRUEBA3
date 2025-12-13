package cl.web2.prueba3.prueba3.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

/**
 * Filtro para agregar información de contexto a los logs (MDC - Mapped Diagnostic Context).
 * Agrega requestId, IP del cliente y método HTTP a cada petición.
 */
@Component
public class LoggingFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        
        try {
            // Agregar ID único de request para rastrear la petición
            String requestId = UUID.randomUUID().toString().substring(0, 8);
            MDC.put("requestId", requestId);
            
            // Agregar IP del cliente
            String clientIp = getClientIP(httpRequest);
            MDC.put("clientIp", clientIp);
            
            // Agregar método HTTP
            MDC.put("method", httpRequest.getMethod());
            
            // Agregar URI
            MDC.put("uri", httpRequest.getRequestURI());
            
            chain.doFilter(request, response);
        } finally {
            // Limpiar MDC después de la petición para evitar memory leaks
            MDC.clear();
        }
    }
    
    /**
     * Obtiene la IP real del cliente considerando proxies y load balancers
     */
    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
