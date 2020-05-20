package tqs.group4.bestofbooks.dto;

import org.junit.jupiter.api.Test;
import tqs.group4.bestofbooks.mocks.OrderMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tqs.group4.bestofbooks.dto.OrderDTO.fromOrder;

public class OrderDTOTests {
    private OrderDTO orderDTO = fromOrder(OrderMocks.order1);

    @Test
    public void testGetId(){
        assertEquals(OrderMocks.order1.getId(), orderDTO.getId());
    }

    @Test
    public void testGetPaymentReference(){
        assertEquals(OrderMocks.order1.getPaymentReference(), orderDTO.getPaymentReference());
    }

    @Test
    public void testGetAddress(){
        assertEquals(OrderMocks.order1.getAddress(), orderDTO.getAddress());
    }

    @Test
    public void testGetFinalPrice(){
        assertEquals(OrderMocks.order1.getFinalPrice(), orderDTO.getFinalPrice());
    }

    @Test
    public void testGetBuyerUsername(){
        assertEquals(OrderMocks.order1.getBuyer().getUsername(), orderDTO.getBuyerName());
    }
}

