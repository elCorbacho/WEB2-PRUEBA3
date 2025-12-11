package cl.web2.prueba3.prueba3.exceptions;

public class BadRequestException extends RuntimeException {
    
    public BadRequestException(String message) {
        super(message);
    }
}
