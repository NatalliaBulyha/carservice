package by.senla.training.bulyha.carservice.exception;

public class ValidatorsException extends RuntimeException {

    public ValidatorsException() {
    }

    public ValidatorsException(String message) {
        super(message);
    }

    public ValidatorsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidatorsException(Throwable cause) {
        super(cause);
    }

    public ValidatorsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
