package top.camsyn.store.commons.exception;

public class NotSelfException extends RuntimeException{
    public NotSelfException() {
    }

    public NotSelfException(String message) {
        super(message);
    }
}
