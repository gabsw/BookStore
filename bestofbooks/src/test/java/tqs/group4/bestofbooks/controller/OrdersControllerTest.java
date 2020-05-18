package tqs.group4.bestofbooks.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.group4.bestofbooks.dto.OrderDTO;
import tqs.group4.bestofbooks.exception.OrderNotFoundException;
import tqs.group4.bestofbooks.model.Order;
import tqs.group4.bestofbooks.service.OrderService;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tqs.group4.bestofbooks.mocks.BuyerMock.buyer1;
import static tqs.group4.bestofbooks.utils.Json.toJson;

@WebMvcTest(OrdersController.class)
public class OrdersControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderService orderService;

    private Order order;

    @BeforeEach
    public void before() {
        order = new Order("AC%EWRGER684654165",
                "77th st no 21, LA, CA, USA",
                10.00,
                buyer1);
        order.setId(23);
    }

    @AfterEach
    public void after() {
        reset(orderService);
    }

    @Test
    void givenExistentId_whenGetOrderByIsbn_thenReturnJson() throws Exception {
        Integer existentId = order.getId();
        String url = "/api/order/" + existentId;
        given(orderService.getOrderById(existentId)).willReturn(OrderDTO.fromOrder(order));

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status()
                .isOk())
           .andExpect(content().json(toJson(OrderDTO.fromOrder(order))));
        verify(orderService, VerificationModeFactory.times(1)).getOrderById(existentId);
    }

    @Test
    void givenBookNotFoundException_thenThrowHTTPStatusNotFound() throws Exception {
        Integer unknownId = 123;
        String url = "/api/order/" + unknownId;

        given(orderService.getOrderById(unknownId)).willThrow(new OrderNotFoundException());

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }
}