package tqs.group4.bestofbooks.controller;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.group4.bestofbooks.dto.OrderDTO;
import tqs.group4.bestofbooks.exception.OrderNotFoundException;
import tqs.group4.bestofbooks.mocks.OrderMocks;
import tqs.group4.bestofbooks.service.OrderService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tqs.group4.bestofbooks.utils.Json.toJson;

@WebMvcTest(BuyersController.class)
public class BuyersControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderService orderService;

    private List<OrderDTO> orders = Lists.newArrayList(OrderDTO.fromOrder(OrderMocks.order1));

    @AfterEach
    public void after() {
        reset(orderService);
    }

    @Test
    void givenExistentBuyer_whenGetOrderByBuyerUsername_thenReturnJson() throws Exception {
        String existentBuyer = "buyer1";
        String url = "/api/buyer/" + existentBuyer + "/orders";
        given(orderService.getOrderByBuyerUsername(existentBuyer)).willReturn(orders);

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status()
                .isOk())
           .andExpect(content().json(toJson(orders)));
        verify(orderService, VerificationModeFactory.times(1)).
                                                                      getOrderByBuyerUsername(existentBuyer);
    }

    @Test
    void givenOrderNotFoundException_thenThrowHTTPStatusNotFound() throws Exception {
        String unknownBuyerUsername = "None";
        String url = "/api/buyer/" + unknownBuyerUsername + "/orders";

        given(orderService.getOrderByBuyerUsername(unknownBuyerUsername)).willThrow(new OrderNotFoundException());

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }
}