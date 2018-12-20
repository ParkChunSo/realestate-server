package kr.ac.skuniv.realestate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends RuntimeException{

    protected Logger logger;

    public GlobalExceptionHandler(){
        logger = LogManager.getLogger(getClass());
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    //@ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleConflictException(Exception e) {
        logger.error(e.getMessage());
        return e.getMessage();
    }
}