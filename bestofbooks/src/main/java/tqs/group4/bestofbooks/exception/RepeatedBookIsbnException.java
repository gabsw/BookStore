package tqs.group4.bestofbooks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RepeatedBookIsbnException extends Exception{

    public RepeatedBookIsbnException(String s) {
        super(s);
    }
}
