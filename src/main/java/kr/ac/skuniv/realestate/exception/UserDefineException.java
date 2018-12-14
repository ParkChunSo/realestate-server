package kr.ac.skuniv.realestate.exception;

public class UserDefineException extends RuntimeException {

    public UserDefineException(String message) {
        super(message);
    }

    public UserDefineException(String message, Throwable cause) {
        super(message, cause);
    }
}
