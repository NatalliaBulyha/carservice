package by.senla.training.bulyha.carservice.handler;

import by.senla.training.bulyha.carservice.dto.ErrorDto;
import by.senla.training.bulyha.carservice.exception.CarServiceBackEndException;
import by.senla.training.bulyha.carservice.exception.InternalException;
import by.senla.training.bulyha.carservice.exception.JwtAuthenticationException;
import by.senla.training.bulyha.carservice.exception.ValidatorsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.io.FileNotFoundException;


@ControllerAdvice
public class RestApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({CarServiceBackEndException.class, InternalException.class, ValidatorsException.class,
            NumberFormatException.class})
    protected ResponseEntity<ErrorDto> handleRestError(Exception e) {
        ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({FileNotFoundException.class, EntityNotFoundException.class, UsernameNotFoundException.class})
    protected ResponseEntity<ErrorDto> handleEntityNotFoundException(Exception e) {
        ErrorDto errorDto = new ErrorDto(HttpStatus.NOT_FOUND, e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<ErrorDto> handleNullPointerException(NullPointerException e) {
        ErrorDto errorDto = new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR, "Server error");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({JwtAuthenticationException.class, BadCredentialsException.class})
    protected ResponseEntity<ErrorDto> handleNullPointerException(Exception e) {
        ErrorDto errorDto = new ErrorDto(HttpStatus.UNAUTHORIZED, e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.UNAUTHORIZED);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        ErrorDto error = new ErrorDto(status, ex.getMessage());
        return super.handleExceptionInternal(ex, error, headers, status, request);
    }
}
