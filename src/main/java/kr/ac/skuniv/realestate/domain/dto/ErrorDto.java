package kr.ac.skuniv.realestate.domain.dto;

import lombok.Data;

@Data
public class ErrorDto {
    private String originalErrorMessage;
    private String errorMessage;
    private String requestURL;

    public ErrorDto(String originalErrorMessage, String errorMessage, String requestURL) {
        this.originalErrorMessage = originalErrorMessage;
        this.errorMessage = errorMessage;
        this.requestURL = requestURL;
    }
}
