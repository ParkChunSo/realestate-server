package kr.ac.skuniv.realestate.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    //WebRequest

    @Autowired
    UrlPathHelper urlPathHelper;

    @ExceptionHandler(UserDefineException.class)
    public ErrorInfo handleUserDefineException(HttpServletRequest request, UserDefineException e) {
        String requestURL = urlPathHelper.getOriginatingRequestUri(request);

        logger.info("======================================");
        logger.info("Time : " + LocalDateTime.now());
        logger.info("RequestMethod : " + request.getMethod());
        logger.info("RequestURL : " + requestURL);
        logger.info("RemoteHost : " + request.getRemoteHost());
        logger.info("ErrorMessage : " + e.getMessage());
        logger.info("Cause : " + e.getCause());
        logger.info("======================================");

        return new ErrorInfo(HttpStatus.BAD_REQUEST.toString(), e.getMessage(), requestURL);
    }

    @ExceptionHandler(Exception.class)
    public ErrorInfo handleException(HttpServletRequest request, Exception e) {
        String requestURL = urlPathHelper.getOriginatingRequestUri(request);

        logger.info("======================================");
        logger.info("Time : " + LocalDateTime.now());
        logger.info("RequestMethod : " + request.getMethod());
        logger.info("RequestURL : " + requestURL);
        logger.info("RemoteHost : " + request.getRemoteHost());
        logger.info("ErrorMessage : " + e.getMessage());
        logger.info("Cause : " + e.getCause());
        logger.info("======================================");

        return new ErrorInfo(HttpStatus.BAD_REQUEST.toString(), "예상치 못한 오류입니다", requestURL);
    }

}
