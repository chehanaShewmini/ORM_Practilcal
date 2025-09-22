package lk.ijse.BO.exceptionHandling;

public class DuplicateException extends RuntimeException {
    public DuplicateException(String message) {
        super(message);
    }
}