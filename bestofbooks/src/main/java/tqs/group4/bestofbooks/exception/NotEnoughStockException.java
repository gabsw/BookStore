package tqs.group4.bestofbooks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotEnoughStockException extends Exception {
    public NotEnoughStockException() {
    }

    public NotEnoughStockException(String s) {
        super(s);
    }
}
