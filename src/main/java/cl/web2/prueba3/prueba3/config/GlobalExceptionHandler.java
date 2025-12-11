package cl.web2.prueba3.prueba3.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import cl.web2.prueba3.prueba3.responses.ApiResponse;
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
}
