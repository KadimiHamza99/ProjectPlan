package io.kadev.handlers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.kadev.exceptions.BusinessLogicException;
import io.kadev.exceptions.ProductNotFoundException;
import io.kadev.exceptions.ProjectNotFoundException;

@RestControllerAdvice
public class BusinessLogicExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleMethodArgumentException(MethodArgumentNotValidException exception) {
		List<String> errors = new ArrayList<>();
		exception.getBindingResult().getFieldErrors().forEach(e->{
			errors.add(e.getDefaultMessage());
		});
		return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<?> handleServiceException(BusinessLogicException exception) {
		return new ResponseEntity<>("Business Logic Exception",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleProductNotFoundException(ProductNotFoundException exception) {
        
        return new ResponseEntity<>("Product Not Found",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<?> handleProjectNotFoundException(ProjectNotFoundException exception) {
        return new ResponseEntity<>("Project Not Found",HttpStatus.INTERNAL_SERVER_ERROR)  ;
    }
    
}
