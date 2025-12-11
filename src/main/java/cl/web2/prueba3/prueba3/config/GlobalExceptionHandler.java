package cl.web2.prueba3.prueba3.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import cl.web2.prueba3.prueba3.responses.ApiResponse;
import cl.web2.prueba3.prueba3.exceptions.ResourceNotFoundException;
import cl.web2.prueba3.prueba3.exceptions.BadRequestException;
import cl.web2.prueba3.prueba3.exceptions.ForbiddenException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.joining(", "));
        
        return ResponseEntity.badRequest().body(
            ApiResponse.<String>builder()
                .status(400)
                .message("Error de validación")
                .error(errors)
                .timestamp(LocalDateTime.now())
                .build()
        );
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleResourceNotFoundException(
            ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ApiResponse.<String>builder()
                .status(404)
                .message("Recurso no encontrado")
                .error(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build()
        );
    }
    
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<String>> handleBadRequestException(
            BadRequestException ex) {
        return ResponseEntity.badRequest().body(
            ApiResponse.<String>builder()
                .status(400)
                .message("Solicitud incorrecta")
                .error(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build()
        );
    }
    
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiResponse<String>> handleForbiddenException(
            ForbiddenException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
            ApiResponse.<String>builder()
                .status(403)
                .message("Acceso denegado")
                .error(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build()
        );
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(
            ApiResponse.<String>builder()
                .status(400)
                .message("Argumento inválido")
                .error(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build()
        );
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<String>> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ApiResponse.<String>builder()
                .status(500)
                .message("Error interno del servidor")
                .error(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build()
        );
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ApiResponse.<String>builder()
                .status(500)
                .message("Error inesperado")
                .error(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build()
        );
    }
}
