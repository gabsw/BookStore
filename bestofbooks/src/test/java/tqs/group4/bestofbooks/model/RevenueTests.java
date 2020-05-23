package tqs.group4.bestofbooks.model;

import org.junit.jupiter.api.Test;
import tqs.group4.bestofbooks.mocks.BookOrderMocks;
import tqs.group4.bestofbooks.mocks.RevenueMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RevenueTests {

    @Test
    public void testGetAmount(){
        assertEquals(150, RevenueMocks.revenue1.getAmount());
    }

    @Test
    public void testGetBookOrder(){
        assertEquals(BookOrderMocks.bookOrder1, RevenueMocks.revenue1.getBookOrder());
    }

    @Test
    public void testGetPublisherName(){
        assertEquals("Publisher 1", RevenueMocks.revenue1.getPublisherName());
    }
}
