package tqs.group4.bestofbooks.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tqs.group4.bestofbooks.exception.InvalidIsbnException;
import tqs.group4.bestofbooks.exception.NullBookException;
import tqs.group4.bestofbooks.mocks.BookMocks;

public class BookOrderTest {

    private BookOrder bookOrder;

    private String buyerUsername = "someUser",
        paymentReference = "AC%87541215485421",
        address = "28 55th, MI, FL, USA",
        isbn;
    private Order order;
    private int quantity = 5;
    private double finalPrice = 10.00;

    Validator validator;
    Set<ConstraintViolation<BookOrder>> violations;

    @BeforeEach
    private void setUp(){
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        order = new Order(paymentReference, buyerUsername, new ArrayList<BookOrder>(), address, finalPrice);
        bookOrder = new BookOrder(BookMocks.infiniteJest, null, quantity);
        isbn = BookMocks.infiniteJest.getIsbn();
    }

    @AfterEach
    private void tearDown(){
        bookOrder = null;
        violations = null;
    }
    
    @Test
    public void testGetOrderBeforeAssignment(){
        assertNull(bookOrder.getOrder());
    }

    @Test
    public void testGetOrderAfterAssignment(){
        bookOrder.setOrder(order);
        assertEquals(order, bookOrder.getOrder());
    }

    @Test
    public void testGetBook(){
        assertEquals(BookMocks.infiniteJest, bookOrder.getBook());
    }

    @Test
    public void testGetQuantity(){
        assertEquals(quantity, bookOrder.getQuantity());
    }

    @Test
    public void testThrowExceptionOnInvalidLengthIsbn(){
        Book badBook = new Book("784545", "Title", "Author", "Description",
            10.00, 20, "category", "publisher");
        assertThrows(InvalidIsbnException.class, () -> {
            new BookOrder(badBook, null, quantity);
        });
    }

    @Test
    public void testThrowExceptionOnInvalidIsbn(){
        Book badBook = new Book("78541236BB125", "Title", "Author", "Description",
            10.00, 20, "category", "publisher");
        assertThrows(InvalidIsbnException.class, () -> {
            new BookOrder(badBook, null, quantity);
        });
    }

    @Test
    public void testThrowExceptionOnNullBook(){
        assertThrows(NullBookException.class, () -> {
            new BookOrder(null, null, quantity);
        });
    }
}