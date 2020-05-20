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
import tqs.group4.bestofbooks.dto.IncomingBookOrderDTO;
import tqs.group4.bestofbooks.dto.IncomingOrderDTO;
import tqs.group4.bestofbooks.dto.OrderDTO;
import tqs.group4.bestofbooks.exception.*;
import tqs.group4.bestofbooks.mocks.BookMocks;
import tqs.group4.bestofbooks.mocks.BookOrderMocks;
import tqs.group4.bestofbooks.mocks.BuyerMock;
import tqs.group4.bestofbooks.model.Order;
import tqs.group4.bestofbooks.service.OrderService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    private int quantity = 2;
    private IncomingBookOrderDTO incomingBookOrderDTO1 = new IncomingBookOrderDTO(BookMocks.onTheRoad.getIsbn(),
            quantity);
    private IncomingBookOrderDTO incomingBookOrderDTO2 = new IncomingBookOrderDTO(BookMocks.onTheRoad.getIsbn(),
            quantity + 1);
    private List<IncomingBookOrderDTO> incomingBookOrderDTOList = new ArrayList(Arrays.asList(incomingBookOrderDTO1, incomingBookOrderDTO2));
    private IncomingOrderDTO incomingOrderDTO = new IncomingOrderDTO(incomingBookOrderDTOList, BuyerMock.buyer1.getUsername(),
            "XYZ", "Address");

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
    void givenBookNotFoundException_whenGetOrderByIsbn_thenThrowHTTPStatusNotFound() throws Exception {
        Integer unknownId = 123;
        String url = "/api/order/" + unknownId;

        given(orderService.getOrderById(unknownId)).willThrow(new OrderNotFoundException());

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    void givenKnownBooks_whenComputePriceForIncomingOrder_thenReturnJson() throws Exception {
        String url = "/api/order/estimated-price";
        given(orderService.computePriceForIncomingOrder(incomingBookOrderDTOList)).willReturn(100.50);

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(incomingBookOrderDTOList))
                .characterEncoding("utf-8")
        ).andExpect(status()
                .isOk())
           .andExpect(content().string(String.valueOf(100.50)));
        verify(orderService, VerificationModeFactory.times(1)).computePriceForIncomingOrder(incomingBookOrderDTOList);
    }

    @Test
    void givenBookNotFoundException_whenComputePriceForIncomingOrder_thenThrowHTTPStatusNotFound() throws Exception {
        String url = "/api/order/estimated-price";
        given(orderService.computePriceForIncomingOrder(incomingBookOrderDTOList)).willThrow(new BookNotFoundException());

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(incomingBookOrderDTOList))
                .characterEncoding("utf-8")
        ).andExpect(status().isNotFound());
    }

    @Test
    void givenKnownBooks_whenCreateOrder_thenReturnJson() throws Exception {
        String url = "/api/order/";
        Order order = new Order("XYZ", "Address", 100.00, buyer1);
        order.addBookOrder(BookOrderMocks.bookOrder1);
        OrderDTO orderDTO = OrderDTO.fromOrder(order);


        given(orderService.createOrderDTO(incomingOrderDTO)).willReturn(orderDTO);

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(incomingOrderDTO))
                .characterEncoding("utf-8")
        ).andExpect(status().isCreated())
           .andExpect(content().json(toJson(orderDTO)));
        verify(orderService, VerificationModeFactory.times(1)).createOrderDTO(incomingOrderDTO);
    }

    @Test
    void givenBookNotFoundException_whenCreateOrder_thenThrowHTTPStatusNotFound() throws Exception {
        String url = "/api/order/";
        given(orderService.createOrderDTO(incomingOrderDTO)).willThrow(new BookNotFoundException());

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(incomingOrderDTO))
                .characterEncoding("utf-8")
        ).andExpect(status().isNotFound());
    }

    @Test
    void givenUserNotFoundException_whenCreateOrder_thenThrowHTTPStatusNotFound() throws Exception {
        String url = "/api/order/";
        given(orderService.createOrderDTO(incomingOrderDTO)).willThrow(new UserNotFoundException());

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(incomingOrderDTO))
                .characterEncoding("utf-8")
        ).andExpect(status().isNotFound());
    }

    @Test
    void givenRepeatedPaymentReferenceException_whenCreateOrder_thenThrowHTTPStatusBadRequest() throws Exception {
        String url = "/api/order/";
        given(orderService.createOrderDTO(incomingOrderDTO)).willThrow(new RepeatedPaymentReferenceException());

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(incomingOrderDTO))
                .characterEncoding("utf-8")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void givenNotEnoughStockException_whenCreateOrder_thenThrowHTTPStatusBadRequest() throws Exception {
        String url = "/api/order/";
        given(orderService.createOrderDTO(incomingOrderDTO)).willThrow(new NotEnoughStockException());

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(incomingOrderDTO))
                .characterEncoding("utf-8")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void givenEmptyIncomingOrderException_whenCreateOrder_thenThrowHTTPStatusBadRequest() throws Exception {
        String url = "/api/order/";
        given(orderService.createOrderDTO(incomingOrderDTO)).willThrow(new EmptyIncomingOrderException());

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(incomingOrderDTO))
                .characterEncoding("utf-8")
        ).andExpect(status().isBadRequest());
    }
}