package kr.ac.skuniv.realestate.exception;

import lombok.Getter;

@Getter
public class UserDefineException extends RuntimeException {

    private String originalMessage;

    public UserDefineException(String message) {
        super(message);
    }

    public UserDefineException(String message, String originalMessage) {
        super(message);
        this.originalMessage = originalMessage;
    }

    public UserDefineException(String message, Throwable cause, String originalMessage) {
        super(message, cause);
        this.originalMessage = originalMessage;
    }
}
