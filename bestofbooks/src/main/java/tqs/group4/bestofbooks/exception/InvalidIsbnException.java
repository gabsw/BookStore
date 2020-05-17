package tqs.group4.bestofbooks.exception;

public class InvalidIsbnException extends RuntimeException {
    public InvalidIsbnException(String message) {
        super(message);
    }

    public InvalidIsbnException() {
    }
}