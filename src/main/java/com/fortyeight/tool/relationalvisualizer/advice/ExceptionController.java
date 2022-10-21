package com.fortyeight.tool.relationalvisualizer.advice;

import com.fortyeight.tool.relationalvisualizer.advice.exception.DataSourceInfoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage processDataSourceInfoException(HttpServletRequest request,
                                                           DataSourceInfoException dataSourceInfoException){
        log.error("DataSourceExceptionInfo error", dataSourceInfoException);
        return new ExceptionMessage(request, dataSourceInfoException);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage processMethodArgumentNotValidException(HttpServletRequest request,
                                                                   MethodArgumentNotValidException argumentNotValidException){
        log.error("MethodArgumentNotValidException error", argumentNotValidException);
        return new ExceptionMessage(request, argumentNotValidException);
    }
}
