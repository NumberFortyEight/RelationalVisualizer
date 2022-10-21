package com.fortyeight.tool.relationalvisualizer.advice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ExceptionMessage {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
    private String exceptionName;
    private String message;
    private String path;

    public ExceptionMessage(HttpServletRequest req, Exception e) {
        this.time = LocalDateTime.now();
        this.exceptionName = e.getClass().getSimpleName();
        this.message = e.getMessage();
        this.path = req.getRequestURI();
    }

    public ExceptionMessage(HttpServletRequest request, Exception e, String message) {
        this.time = LocalDateTime.now();
        this.exceptionName = e.getClass().getSimpleName();
        this.message = message;
        this.path = request.getRequestURI();
    }
}