package tqs.group4.bestofbooks.controller;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tqs.group4.bestofbooks.exception.OrderNotFoundException;
import tqs.group4.bestofbooks.mocks.BookMocks;
import tqs.group4.bestofbooks.model.BookOrder;
import tqs.group4.bestofbooks.model.Order;
import tqs.group4.bestofbooks.service.OrderService;

@WebMvcTest(BuyersController.class)
public class BuyersControllerTest {

//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private OrderService orderService;
//
//    List<BookOrder> bookOrders1, bookOrders2;
//    List<Order> orders;
//    Order order1, order2;
//
//    BookOrder bookOrder1, bookOrder2;
//
//    String username = "someUser", invalidUsername = "unknown";
//
//    @BeforeEach
//    public void setUp(){
//        bookOrder1 = new BookOrder(BookMocks.onTheRoad, null, 3);
//        bookOrder2 = new BookOrder(BookMocks.infiniteJest, null, 2);
//        bookOrders1 = new ArrayList<>();
//        bookOrders1.add(bookOrder1);
//        bookOrders2 = new ArrayList<>();
//        bookOrders2.add(bookOrder2);
//        order1 = new Order("AC%EWRGER684654165", "someUser", bookOrders1, "77th st no 21, LA, CA, USA", 10.00);
//        order1.setId(1);
//        bookOrder1.setOrder(order1);
//        order2 = new Order("AC%EWRGER684989898", "someUser", bookOrders2, "77th st no 21, LA, CA, USA", 10.00);
//        order2.setId(2);
//        bookOrder2.setOrder(order2);
//        for (BookOrder bookOrder: bookOrders1)
//            bookOrder.setOrder(order1);
//        for (BookOrder bookOrder: bookOrders2)
//            bookOrder.setOrder(order2);
//        orders = new ArrayList<>();
//        orders.add(order1);
//        orders.add(order2);
//    }
//
//    @AfterEach
//    public void tearDown(){
//        bookOrders1 = null;
//        bookOrders2 = null;
//        order1 = null;
//        order2 = null;
//        orders = null;
//        reset(orderService);
//    }
//
//    @Test
//    public void givenValidId_whenGetOrderById_thenReturnJson() throws Exception {
//        String url = "/api/buyer/" + username + "/orders";
//
//        given(orderService.getByBuyerUsername(username)).willReturn(orders);
//
//        mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
//            .andDo(print())
//            .andExpect(status().isOk())
//            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//            .andExpect(jsonPath("$", hasSize(orders.size())))
//            .andExpect(jsonPath("$[0].id", is(orders.get(0).getId())))
//            .andExpect(jsonPath("$[0].paymentReference", is(orders.get(0).getPaymentReference())))
//            .andExpect(jsonPath("$[0].buyerUsername", is(orders.get(0).getBuyerUsername())))
//            .andExpect(jsonPath("$[0].bookOrders", hasSize(orders.get(0).getBookOrders().size())))
//            .andExpect(jsonPath("$[0].bookOrders[0].book.isbn", is(orders.get(0).getBookOrders().get(0).getBook().getIsbn())))
//            .andExpect(jsonPath("$[0].bookOrders[0].quantity", is(orders.get(0).getBookOrders().get(0).getQuantity())))
//            .andExpect(jsonPath("$[0].address", is(orders.get(0).getAddress())))
//            .andExpect(jsonPath("$[0].finalPrice", is(orders.get(0).getFinalPrice())))
//            .andExpect(jsonPath("$[1].id", is(orders.get(1).getId())))
//            .andExpect(jsonPath("$[1].paymentReference", is(orders.get(1).getPaymentReference())))
//            .andExpect(jsonPath("$[1].buyerUsername", is(orders.get(1).getBuyerUsername())))
//            .andExpect(jsonPath("$[1].bookOrders", hasSize(orders.get(1).getBookOrders().size())))
//            .andExpect(jsonPath("$[1].bookOrders[0].book.isbn", is(orders.get(1).getBookOrders().get(0).getBook().getIsbn())))
//            .andExpect(jsonPath("$[1].bookOrders[0].quantity", is(orders.get(1).getBookOrders().get(0).getQuantity())))
//            .andExpect(jsonPath("$[1].address", is(orders.get(1).getAddress())))
//            .andExpect(jsonPath("$[1].finalPrice", is(orders.get(1).getFinalPrice())))
//        ;
//        verify(orderService, VerificationModeFactory.times(1)).getByBuyerUsername(username);
//    }
//
//    @Test
//    public void givenOrderNotFoundException_whenGetByBuyerUsername_thenThrowHTTPStatusNotFound () throws Exception {
//        String url = "/api/buyer/" + invalidUsername + "/orders";
//
//        given(orderService.getByBuyerUsername(invalidUsername)).willThrow(new OrderNotFoundException());
//
//        mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNotFound());
//        verify(orderService, VerificationModeFactory.times(1)).getByBuyerUsername(invalidUsername);
//    }
//
}