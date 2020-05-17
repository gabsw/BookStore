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

@WebMvcTest(OrdersController.class)
public class OrdersControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderService service;

    int validId = 1, invalidId = 2;

    List<BookOrder> bookOrders;
    Order order;

    @BeforeEach
    public void setUp(){
        bookOrders = new ArrayList<>();
        bookOrders.add(new BookOrder(BookMocks.infiniteJest, null, 2));
        order = new Order("AC%EWRGER684654165", "someUser", bookOrders, "77th st no 21, LA, CA, USA", 10.00);
        order.setId(1);
        for (BookOrder bookOrder: bookOrders)
            bookOrder.setOrder(order);
    }

    @AfterEach
    public void tearDown(){
        bookOrders = null;
        order = null;
        reset(service);
    }

    @Test
    public void givenValidId_whenGetOrderById_thenReturnJson() throws Exception {
        String url = "/api/order/id/" + validId;

        given(service.getById(validId)).willReturn(order);

        mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(order.getId())))
            .andExpect(jsonPath("$.paymentReference", is(order.getPaymentReference())))
            .andExpect(jsonPath("$.buyerUsername", is(order.getBuyerUsername())))
            .andExpect(jsonPath("$.bookOrders", hasSize(order.getBookOrders().size())))
            .andExpect(jsonPath("$.bookOrders[0].book.isbn", is(order.getBookOrders().get(0).getBook().getIsbn())))
            .andExpect(jsonPath("$.bookOrders.[0].quantity", is(order.getBookOrders().get(0).getQuantity())))
        ;
        verify(service, VerificationModeFactory.times(1)).getById(validId);
    }

    @Test
    public void givenOrderNotFoundException_whenGetById_thenThrowHTTPStatusNotFound () throws Exception {
        String url = "/api/order/id/" + invalidId;

        given(service.getById(invalidId)).willThrow(new OrderNotFoundException());

        mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
        verify(service, VerificationModeFactory.times(1)).getById(invalidId);
    }
}