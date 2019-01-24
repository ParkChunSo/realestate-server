package kr.ac.skuniv.realestate.exception;

import lombok.Data;

@Data
public class ErrorInfo {
    private String errorMessage;
    private String requestURL;

    public ErrorInfo(String errorMessage, String requestURL) {
        this.errorMessage = errorMessage;
        this.requestURL = requestURL;
    }
}