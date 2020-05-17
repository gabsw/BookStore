package tqs.group4.bestofbooks.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

//import tqs.group4.bestofbooks.dto.RequestOrderDTO;
import tqs.group4.bestofbooks.exception.OrderNotFoundException;
import tqs.group4.bestofbooks.mocks.BookMocks;
import tqs.group4.bestofbooks.model.BookOrder;
import tqs.group4.bestofbooks.model.Order;
import tqs.group4.bestofbooks.repository.OrderRepository;

public class OrderServiceTest {

//    //private RequestOrderDTO requestOrderDTO;
//    private Order order;
//    private List<Order> orders;
//    private List<BookOrder> bookOrders1, bookOrders2;
//
//    private String paymentReference1 = "AS%6845416846R5G4R5G4", paymentReference2 = "IP%6845416846R5G8R5D4",
//        buyerUsername = "someBuyer", address = "85 at 12 st, SF, CA, USA";
//    private double finalPrice1 = 10.00, finalPrice2 = 15.00;
//    private int quant1 = 2, quant2 = 3, quant3 = 2;
//
//    @Mock
//    private OrderRepository repo;
//
//    @InjectMocks
//    private OrderService service;
//
//    @BeforeEach
//    public void setUp(){
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @AfterEach
//    public void tearDown(){
//        //requestOrderDTO = null;
//        order = null;
//        orders = null;
//        bookOrders1 = null;
//        bookOrders2 = null;
//    }
//
//    @Test
//    public void testGetById(){
//        try {
//            bookOrders1 = new ArrayList<BookOrder>();
//            bookOrders1.add(new BookOrder());
//            order = new Order(paymentReference1, buyerUsername, bookOrders1, address, finalPrice1);
//            when(repo.findById(anyInt())).thenReturn(order);
//
//            assertEquals(order, service.getById(3));
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testGetByUnknownId_OrderNotFoundException(){
//        when(repo.findById(anyInt())).thenReturn(null);
//
//        assertThrows(OrderNotFoundException.class, () -> {
//            service.getById(3);
//        });
//    }
//
//    @Test
//    public void testGetByBuyerUsername(){
//        bookOrders1 = new ArrayList<BookOrder>();
//        bookOrders2 = new ArrayList<BookOrder>();
//        bookOrders1.add(new BookOrder(BookMocks.infiniteJest, null, quant1));
//        bookOrders1.add(new BookOrder(BookMocks.onTheRoad, null, quant3));
//        bookOrders2.add(new BookOrder(BookMocks.infiniteJest, null, quant2));
//        bookOrders2.add(new BookOrder(BookMocks.onTheRoad, null, quant3));
//        orders = new ArrayList<Order>();
//        orders.add(new Order(paymentReference1, buyerUsername, bookOrders1, address, finalPrice1));
//        orders.add(new Order(paymentReference2, buyerUsername, bookOrders2, address, finalPrice2));
//
//        when(repo.findByBuyerUsername(anyString())).thenReturn(orders);
//
//        assertEquals(orders, service.getByBuyerUsername("someBuyer"));
//    }
//
//    @Test
//    public void testGetByUnknownBuyerUsername_OrderNotFoundException(){
//        when(repo.findByBuyerUsername(anyString())).thenReturn(null);
//
//        assertThrows(OrderNotFoundException.class,
//        () -> {
//            service.getByBuyerUsername("someBuyer");
//        }
//        );
//    }
}