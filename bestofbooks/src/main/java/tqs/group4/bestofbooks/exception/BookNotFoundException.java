package tqs.group4.bestofbooks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BookNotFoundException extends Exception {
    public BookNotFoundException(String isbn) {
        super("Book with " + isbn + " was not found in the platform.");
    }

    public BookNotFoundException() {
    }
}
