package lk.ijse.BO.exceptionHandling;

public class InUseException extends RuntimeException {
    public InUseException(String message) {
        super(message);
    }
}