package tqs.group4.bestofbooks.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tqs.group4.bestofbooks.model.BookOrder;

public class RequestOrderDTOTest {
    
    List<BookOrder> bookOrders;
    
    RequestOrderDTO requestOrderDTO;

    String isbn1 = "1112223335125", isbn2 = "4875963215485", 
        buyerUsername = "user_1", paymentReference = "843854%16WE8G45RG4",
        address = "50th ave at 2nd st, NY, NY, USA";

    int quant1 = 2, quant2 = 3;

    Validator validator;
    Set<ConstraintViolation<RequestOrderDTO>> violations;

    @BeforeEach
    public void setUp(){
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        bookOrders = new ArrayList<>();
    }

    @AfterEach
    public void tearDown(){
        bookOrders = null;
        requestOrderDTO = null;
        violations = null;
    }

    @Test
    public void whenNoConstraintViolations_thenReturnNoConstraintViolations(){
        bookOrders.add(new BookOrder());
        bookOrders.add(new BookOrder());
        requestOrderDTO = new RequestOrderDTO(bookOrders, buyerUsername, paymentReference, address);
        violations = validator.validate(requestOrderDTO);

        assertEquals(0, violations.size());
    }

    @Test
    public void whenEmptyIsbns_thenReturnOneConstraintViolation(){
        requestOrderDTO = new RequestOrderDTO(bookOrders, buyerUsername, paymentReference, address);
        violations = validator.validate(requestOrderDTO);

        assertEquals(1, violations.size());
    }

    @Test
    public void whenNullBuyerUsernamePaymentReferenceAddress_thenReturnThreeConstraintViolations(){
        bookOrders.add(new BookOrder());
        requestOrderDTO = new RequestOrderDTO(bookOrders, null, null, null);
        violations = validator.validate(requestOrderDTO);

        assertEquals(3, violations.size());
    }
}