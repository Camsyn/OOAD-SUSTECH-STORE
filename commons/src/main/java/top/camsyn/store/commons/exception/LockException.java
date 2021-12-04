package top.camsyn.store.commons.exception;

public class LockException extends Exception{
    public LockException() {
    }

    public LockException(String message) {
        super(message);
    }

    public LockException(Throwable cause) {
        super(cause);
    }
}
