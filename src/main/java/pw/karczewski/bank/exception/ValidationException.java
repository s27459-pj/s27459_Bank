package pw.karczewski.bank.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String field, String message) {
        super(field + " " + message);
    }
}
