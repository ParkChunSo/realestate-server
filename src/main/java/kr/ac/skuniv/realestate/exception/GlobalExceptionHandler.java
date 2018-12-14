package kr.ac.skuniv.realestate.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserDefineException.class)
    public ResponseEntity<?> handleUserDefineException(HttpServletRequest request, UserDefineException e) {

        logger.info("Time : "+ LocalDateTime.now());
        logger.info("RequestMethod : "+ request.getMethod());
        logger.info("RequestURL : " + request.getRequestURL());
        logger.info("RemoteHost : "+ request.getRemoteHost());
        logger.info("ErrorMessage : " + e.getMessage());
        logger.info("Cause : " + e.getCause());

        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?> handleException(HttpServletRequest request, Exception e) {

        logger.info("Time : "+ LocalDateTime.now());
        logger.info("RequestMethod : "+ request.getMethod());
        logger.info("RequestURL : " + request.getRequestURL());
        logger.info("RemoteHost : "+ request.getRemoteHost());
        logger.info("ErrorMessage : " + e.getMessage());
        logger.info("Cause : " + e.getCause());

        return new ResponseEntity<>("예상치 못한 오류입니다.", HttpStatus.BAD_REQUEST);
    }


}
