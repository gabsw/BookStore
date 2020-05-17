package tqs.group4.bestofbooks.model;

import org.junit.jupiter.api.Test;
import tqs.group4.bestofbooks.mocks.BookMocks;
import tqs.group4.bestofbooks.mocks.BookOrderMocks;
import tqs.group4.bestofbooks.mocks.OrderMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookOrderTest {

    @Test
    public void testGetBook() {
        assertEquals(BookMocks.onTheRoad, BookOrderMocks.bookOrder1.getBook());
    }

    @Test
    public void testGetQuantity() {
        assertEquals(2, BookOrderMocks.bookOrder1.getQuantity());
    }

    @Test
    public void testGetOrder() {
        assertEquals(OrderMocks.order1, BookOrderMocks.bookOrder1.getOrder());
    }
}