package tqs.group4.bestofbooks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RegistrationFailedException extends Exception{

	public RegistrationFailedException() {
    }

    public RegistrationFailedException(String s) {
        super(s);
    }
}
