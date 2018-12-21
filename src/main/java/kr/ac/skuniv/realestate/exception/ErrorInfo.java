package kr.ac.skuniv.realestate.exception;

import lombok.Data;

@Data
public class ErrorInfo {

    private String statusCode;
    private String message;
    private String requestURL;

    public ErrorInfo(String statusCode, String message, String requestURL) {
        this.statusCode = statusCode;
        this.message = message;
        this.requestURL = requestURL;
    }
}
