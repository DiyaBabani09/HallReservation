package com.example.HallReservation.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalHandlerException {
    @ExceptionHandler(ProfessorHandleException.class)
    public ResponseEntity<?> productExceptionHandler(ProfessorHandleException professorHandleException){
        ErrorResponse errorResponse=new ErrorResponse(LocalDateTime.now(),
            professorHandleException.getMessage(),"professor not found");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(HallHandlerException.class)
public ResponseEntity<?>hallExceptionHandler(HallHandlerException hallHandlerException){
        ErrorResponse er=new ErrorResponse(LocalDateTime.now(),
                hallHandlerException.getMessage(),"hall is not available");
        return new ResponseEntity<>(er,HttpStatus.NOT_FOUND);
}
}
