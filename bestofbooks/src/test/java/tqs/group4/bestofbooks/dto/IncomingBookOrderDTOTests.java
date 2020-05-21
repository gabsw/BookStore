package tqs.group4.bestofbooks.dto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IncomingBookOrderDTOTests {
    private String isbn = "1111111111111";
    private int quantity = 2;
    private IncomingBookOrderDTO incomingBookOrderDTO = new IncomingBookOrderDTO(isbn, quantity);
    Validator validator;
    Set<ConstraintViolation<IncomingBookOrderDTO>> violations;

    @BeforeEach
    public void setUp(){
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @AfterEach
    public void tearDown(){
        violations = null;
    }

    @Test
    public void testGetIsbn(){
        assertEquals(isbn, incomingBookOrderDTO.getIsbn());
    }

    @Test
    public void testGetQuantity(){
        assertEquals(quantity, incomingBookOrderDTO.getQuantity());
    }

    @Test
    public void whenBadIsbn_thenReturnOneConstraintViolation(){
        IncomingBookOrderDTO incomingBadIsbnOrderDTO = new IncomingBookOrderDTO("1", quantity);
        violations = validator.validate(incomingBadIsbnOrderDTO);

        assertEquals(1, violations.size());
    }

    @Test
    public void whenBadQuantity_thenReturnOneConstraintViolation(){
        IncomingBookOrderDTO incomingBadQuantityOrderDTO = new IncomingBookOrderDTO(isbn, -3);
        violations = validator.validate(incomingBadQuantityOrderDTO);

        assertEquals(1, violations.size());
    }
}
