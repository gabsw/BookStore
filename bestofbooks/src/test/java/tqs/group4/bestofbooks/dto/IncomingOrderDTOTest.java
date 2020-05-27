package tqs.group4.bestofbooks.dto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IncomingOrderDTOTest {

    List<IncomingBookOrderDTO> incomingBookOrderDTOS;
    
    IncomingOrderDTO incomingOrderDTO;

    String isbn1 = "1112223335125", isbn2 = "4875963215485", 
        buyerUsername = "user_1", paymentReference = "843854%16WE8G45RG4",
        address = "50th ave at 2nd st, NY, NY, USA";

    int quant1 = 2, quant2 = 3;

    Validator validator;
    Set<ConstraintViolation<IncomingOrderDTO>> violations;

    @BeforeEach
    public void setUp(){
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        incomingBookOrderDTOS = new ArrayList<>(Arrays.asList(new IncomingBookOrderDTO(isbn1, 2)));
    }

    @AfterEach
    public void tearDown(){
        incomingBookOrderDTOS = null;
        incomingOrderDTO = null;
        violations = null;
    }

    @Test
    public void testGetIncomingBookOrderDTO(){
        incomingOrderDTO = new IncomingOrderDTO(incomingBookOrderDTOS, buyerUsername, paymentReference, address);
        assertEquals(incomingBookOrderDTOS, incomingOrderDTO.getIncomingBookOrderDTOS());
    }

    @Test
    public void testGetBuyerUsername(){
        incomingOrderDTO = new IncomingOrderDTO(incomingBookOrderDTOS, buyerUsername, paymentReference, address);
        assertEquals(buyerUsername, incomingOrderDTO.getBuyerUsername());
    }

    @Test
    public void testGetPaymentReference(){
        incomingOrderDTO = new IncomingOrderDTO(incomingBookOrderDTOS, buyerUsername, paymentReference, address);
        assertEquals(paymentReference, incomingOrderDTO.getPaymentReference());
    }

    @Test
    public void testGetAddress(){
        incomingOrderDTO = new IncomingOrderDTO(incomingBookOrderDTOS, buyerUsername, paymentReference, address);
        assertEquals(address, incomingOrderDTO.getAddress());
    }

    @Test
    public void whenNoConstraintViolations_thenReturnNoConstraintViolations(){
        incomingOrderDTO = new IncomingOrderDTO(incomingBookOrderDTOS, buyerUsername, paymentReference, address);
        violations = validator.validate(incomingOrderDTO);

        assertEquals(0, violations.size());
    }

    @Test
    public void whenEmptyBooks_thenReturnOneConstraintViolation(){
        List<IncomingBookOrderDTO> emptyBookOrder = new ArrayList<>();
        incomingOrderDTO = new IncomingOrderDTO(emptyBookOrder, buyerUsername, paymentReference, address);
        violations = validator.validate(incomingOrderDTO);

        assertEquals(1, violations.size());
    }

    @Test
    public void whenBadBuyerUsername_thenReturnOneConstraintViolation(){
        incomingOrderDTO = new IncomingOrderDTO(incomingBookOrderDTOS, "onetwothreefourfivesixseveneight", paymentReference, address);
        violations = validator.validate(incomingOrderDTO);

        assertEquals(1, violations.size());
    }

    @Test
    public void whenBadPaymentReference_thenReturnOneConstraintViolation(){
        incomingOrderDTO = new IncomingOrderDTO(incomingBookOrderDTOS, buyerUsername, "111111111111111111111", address);
        violations = validator.validate(incomingOrderDTO);

        assertEquals(1, violations.size());
    }

    @Test
    public void whenBadAddress_thenReturnOneConstraintViolation(){
        StringBuilder badAddress = new StringBuilder();
        for (int i = 0; i < 60; i++) {
            badAddress.append(i);
        }

        incomingOrderDTO = new IncomingOrderDTO(incomingBookOrderDTOS, buyerUsername, paymentReference, badAddress.toString());
        violations = validator.validate(incomingOrderDTO);

        assertEquals(1, violations.size());
    }

    @Test
    public void whenNullBuyerUsernamePaymentReferenceAddress_thenReturnThreeConstraintViolations(){
        incomingOrderDTO = new IncomingOrderDTO(incomingBookOrderDTOS, null, null, null);
        violations = validator.validate(incomingOrderDTO);

        assertEquals(3, violations.size());
    }
}
