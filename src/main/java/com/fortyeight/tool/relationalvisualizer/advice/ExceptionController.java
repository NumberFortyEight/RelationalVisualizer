package com.fortyeight.tool.relationalvisualizer.advice;

import com.fortyeight.tool.relationalvisualizer.advice.exception.DataSourceInfoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage processDataSourceInfoException(HttpServletRequest request,
                                                           DataSourceInfoException dataSourceInfoException) {
        log.error("DataSourceExceptionInfo error", dataSourceInfoException);
        return new ExceptionMessage(request, dataSourceInfoException);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionMessage processMethodArgumentNotValidException(HttpServletRequest request,
                                                                   MethodArgumentNotValidException argumentNotValidException) {
        log.error("MethodArgumentNotValidException error", argumentNotValidException);
        Map<String, String> errors = new HashMap<>();
        argumentNotValidException.getBindingResult()
                .getAllErrors()
                .forEach((error) -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });
        return new ExceptionMessage(request, argumentNotValidException, errors.toString());
    }
}
