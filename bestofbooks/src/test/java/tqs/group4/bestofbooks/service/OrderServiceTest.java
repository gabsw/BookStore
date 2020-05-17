package tqs.group4.bestofbooks.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

//import tqs.group4.bestofbooks.dto.RequestOrderDTO;
import tqs.group4.bestofbooks.dto.BookOrderDTO;
import tqs.group4.bestofbooks.dto.OrderDTO;
import tqs.group4.bestofbooks.exception.OrderNotFoundException;
import tqs.group4.bestofbooks.mocks.OrderMocks;
import tqs.group4.bestofbooks.model.Order;
import tqs.group4.bestofbooks.repository.OrderRepository;

public class OrderServiceTest {
    @Mock
    private OrderRepository repo;

    @InjectMocks
    private OrderService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    public void compareListOrderDTO(List<OrderDTO> list1, List<OrderDTO> list2) {
        assertEquals(list1.size(), list2.size());
        for (int i = 0; i < list1.size(); i++) {
            compareOrderDTO(list1.get(i), list2.get(i));
        }
    }

    public void compareOrderDTO(OrderDTO thisOrder, OrderDTO thatOrder) {
        assertAll("comparingOrders",
                () -> assertEquals(thisOrder.getId(), thatOrder.getId()),
                () -> assertEquals(thisOrder.getAddress(), thatOrder.getAddress()),
                () -> assertEquals(thisOrder.getFinalPrice(), thatOrder.getFinalPrice()),
                () -> assertEquals(thisOrder.getPaymentReference(), thatOrder.getPaymentReference()),
                () -> compareListOfBookOrderDTO(thisOrder.getBookOrders(), thatOrder.getBookOrders())
        );
    }

    public void compareListOfBookOrderDTO(List<BookOrderDTO> list1, List<BookOrderDTO> list2) {
        assertEquals(list1.size(), list2.size());
        for (int i = 0; i < list1.size(); i++) {
            compareBookOrderDTO(list1.get(i), list2.get(i));
        }
    }

    public void compareBookOrderDTO(BookOrderDTO bookOrderDTO1, BookOrderDTO bookOrderDTO2) {
        assertAll(
                () -> assertEquals(bookOrderDTO1.getIsbn(), bookOrderDTO2.getIsbn()),
                () -> assertEquals(bookOrderDTO1.getAuthor(), bookOrderDTO2.getAuthor()),
                () -> assertEquals(bookOrderDTO1.getQuantity(), bookOrderDTO2.getQuantity()),
                () -> assertEquals(bookOrderDTO1.getTitle(), bookOrderDTO2.getTitle())
        );
    }

    @Test
    public void testGetOrderByKnownId() {
        when(repo.findById(anyInt())).thenReturn(OrderMocks.order1);
        compareOrderDTO(OrderDTO.fromOrder(OrderMocks.order1), service.getOrderById(3));
    }

    @Test
    public void testGetOrderByUnknownId_OrderNotFoundException() {
        when(repo.findById(anyInt())).thenReturn(null);
        assertThrows(OrderNotFoundException.class, () -> {
            service.getOrderById(3);
        });
    }

    @Test
    public void testGetOrderByKnownBuyerUsername() {
        List<Order> orders = Lists.newArrayList(OrderMocks.order1);
        when(repo.findByBuyerUsername(anyString())).thenReturn(orders);
        compareListOrderDTO(orders.stream().map(OrderDTO::fromOrder).collect(Collectors.toList()),
                service.getOrderByBuyerUsername("someBuyer"));
    }

    @Test
    public void testGetOrderByUnknownBuyerUsername_OrderNotFoundException() {
        when(repo.findByBuyerUsername(anyString())).thenReturn(new ArrayList<Order>());

        assertThrows(OrderNotFoundException.class,
                () -> {
                    service.getOrderByBuyerUsername("someBuyer");
                }
        );
    }
}