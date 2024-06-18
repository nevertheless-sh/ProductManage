package kr.co.hanbit.product.management.presentation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ErrorMessage>> handleConstraintViolatedException(
            ConstraintViolationException ex
    ) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        List<ErrorMessage> errors = constraintViolations.stream()
                .map(
                        constraintViolation ->
                                new ErrorMessage(
                                        constraintViolation.getPropertyPath() + ", " +
                                                constraintViolation.getMessage()
                                )
                )
                .toList();

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
