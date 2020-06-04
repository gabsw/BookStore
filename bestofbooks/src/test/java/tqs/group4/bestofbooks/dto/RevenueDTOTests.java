package tqs.group4.bestofbooks.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tqs.group4.bestofbooks.mocks.BookMocks;
import tqs.group4.bestofbooks.mocks.BookOrderMocks;
import tqs.group4.bestofbooks.model.BookOrder;
import tqs.group4.bestofbooks.model.Order;
import tqs.group4.bestofbooks.model.Revenue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tqs.group4.bestofbooks.dto.RevenueDTO.fromRevenue;
import static tqs.group4.bestofbooks.mocks.BuyerMock.buyer1;

public class RevenueDTOTests {

    double price = 10.00;
    Order order1 = new Order(
            "AC%EWRGER684654165",
            "77th st no 21, LA, CA, USA",
            price,
            buyer1
    );
    BookOrder bookOrder1;
    Revenue revenue1;
    RevenueDTO revenueDTO;
    @BeforeEach
    public void setUp() {
        order1.setId(2);
        bookOrder1 = new BookOrder(BookMocks.onTheRoad, order1, 1);
        revenue1 = new Revenue(price * 0.80, bookOrder1, "Publisher 1");
        revenueDTO = fromRevenue(revenue1);

    }
    @Test
    public void testGetID(){
        revenueDTO.setId(2);
        assertEquals(2, revenueDTO.getId());
    }

    @Test
    public void testGetAmount(){
        assertEquals(price * 0.80, revenueDTO.getAmount());
    }

    @Test
    public void testGetPublisherName(){
        assertEquals("Publisher 1", revenueDTO.getPublisherName());
    }

    @Test
    public void testGetIsbn(){
        assertEquals(BookOrderMocks.bookOrder1.getBook().getIsbn(), revenueDTO.getIsbn());
    }

    @Test
    public void testGetOrderId(){
        order1.setId(2);
        assertEquals(2, revenueDTO.getOrderId());
    }

}
