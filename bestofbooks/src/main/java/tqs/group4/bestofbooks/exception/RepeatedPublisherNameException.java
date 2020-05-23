package tqs.group4.bestofbooks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RepeatedPublisherNameException extends Exception{

	public RepeatedPublisherNameException() {
    }

    public RepeatedPublisherNameException(String s) {
        super(s);
    }
}
