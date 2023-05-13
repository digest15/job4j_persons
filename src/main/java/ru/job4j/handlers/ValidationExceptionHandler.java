package ru.job4j.handlers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@ControllerAdvice()
@Slf4j
@AllArgsConstructor
public class ValidationExceptionHandler {

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e) throws IOException {
        List<Map<String, String>> violations = e.getFieldErrors().stream()
                .map(f -> Map.of(
                                f.getField(),
                                String.format("%s. Actual value: %s", f.getDefaultMessage(), f.getRejectedValue())
                        )
                ).toList();

        return ResponseEntity.badRequest().body(violations);
    }
}
