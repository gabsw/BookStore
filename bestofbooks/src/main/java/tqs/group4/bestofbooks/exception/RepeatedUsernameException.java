package tqs.group4.bestofbooks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RepeatedUsernameException extends Exception {

	public RepeatedUsernameException() {
    }

    public RepeatedUsernameException(String s) {
        super(s);
    }
}
