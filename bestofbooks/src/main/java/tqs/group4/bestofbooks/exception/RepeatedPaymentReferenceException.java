package tqs.group4.bestofbooks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RepeatedPaymentReferenceException extends Exception {
    public RepeatedPaymentReferenceException() {
    }

    public RepeatedPaymentReferenceException(String s) {
        super(s);
    }
}
