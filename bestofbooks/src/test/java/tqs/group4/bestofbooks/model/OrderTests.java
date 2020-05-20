package tqs.group4.bestofbooks.model;

import org.junit.jupiter.api.Test;
import tqs.group4.bestofbooks.mocks.BookOrderMocks;
import tqs.group4.bestofbooks.mocks.BuyerMock;
import tqs.group4.bestofbooks.mocks.OrderMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTests {

    private Order order = new Order(null, null, null, null);

    @Test
    public void testGetPaymentReference(){
        assertEquals("AC%EWRGER684654165", OrderMocks.order1.getPaymentReference());
    }

    @Test
    public void testGetBuyerUsername(){
        assertEquals(BuyerMock.buyer1, OrderMocks.order1.getBuyer());
    }

    @Test
    public void testGetAddress(){
        assertEquals("77th st no 21, LA, CA, USA", OrderMocks.order1.getAddress());
    }

    @Test
    public void testGetFinalPrice(){
        assertEquals(10.00, OrderMocks.order1.getFinalPrice());
    }

    @Test
    public void testSizeAfterAddBookOrder(){
        int size = order.getBookOrders().size();
        order.addBookOrder(BookOrderMocks.bookOrder1);
        assertEquals(size + 1, order.getBookOrders().size());
    }
}