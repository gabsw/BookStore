package tqs.group4.bestofbooks.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RevenueDTOTests {

    RevenueDTO revenueDTO  = new RevenueDTO(2, 100, "Publisher 1", 1,
            "1111111111111");
    @Test
    public void testGetID(){
        assertEquals(2, revenueDTO.getId());
    }

    @Test
    public void testGetAmount(){
        assertEquals(100, revenueDTO.getAmount());
    }

    @Test
    public void testGetPublisherName(){
        assertEquals("Publisher 1", revenueDTO.getPublisherName());
    }

    @Test
    public void testGetIsbn(){
        assertEquals("1111111111111", revenueDTO.getIsbn());
    }

    @Test
    public void testGetOrderId(){
        assertEquals(1, revenueDTO.getOrderId());
    }

}
