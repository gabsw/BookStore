package tqs.group4.bestofbooks.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.group4.bestofbooks.BestofbooksApplication;
import tqs.group4.bestofbooks.dto.IncomingBookOrderDTO;
import tqs.group4.bestofbooks.dto.IncomingOrderDTO;
import tqs.group4.bestofbooks.dto.OrderDTO;
import tqs.group4.bestofbooks.mocks.BookMocks;
import tqs.group4.bestofbooks.mocks.BookOrderMocks;
import tqs.group4.bestofbooks.mocks.BuyerMock;
import tqs.group4.bestofbooks.mocks.OrderMocks;
import tqs.group4.bestofbooks.model.BookOrder;
import tqs.group4.bestofbooks.model.Order;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tqs.group4.bestofbooks.mocks.BuyerMock.buyer1;
import static tqs.group4.bestofbooks.utils.Json.toJson;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = BestofbooksApplication.class)
@AutoConfigureMockMvc
@Transactional
public class OrderControllerIT {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private EntityManager entityManager;

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
        entityManager.createNativeQuery("TRUNCATE books, orders, commissions, books_orders, revenues").executeUpdate();
        entityManager.createNativeQuery("ALTER SEQUENCE orders_id_seq RESTART WITH 1").executeUpdate();
        order = new Order("AC%EWRGER684654165",
                "77th st no 21, LA, CA, USA",
                10.00,
                buyer1);
    }

    @Test
    void givenExistentId_whenGetOrderByIsbn_thenReturnJson() throws Exception {
        entityManager.persist(order);
        entityManager.flush();

        int existentId = 1;
        String url = "/api/order/" + existentId;

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status()
                .isOk())
           .andExpect(content().json(toJson(OrderDTO.fromOrder(order))));
    }

    @Test
    void givenBookNotFoundException_whenGetOrderByIsbn_thenThrowHTTPStatusNotFound() throws Exception {
        int unknownId = 123979;
        String url = "/api/order/" + unknownId;

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    void givenKnownBooks_whenComputePriceForIncomingOrder_thenReturnJson() throws Exception {
        entityManager.persist(BookMocks.onTheRoad);
        entityManager.flush();
        String url = "/api/order/estimated-price";

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(incomingBookOrderDTOList))
                .characterEncoding("utf-8")
        ).andExpect(status()
                .isOk())
           .andExpect(content().string(String.valueOf(BookMocks.onTheRoad.getPrice() * 5)));
    }

    @Test
    void givenBookNotFoundException_whenComputePriceForIncomingOrder_thenThrowHTTPStatusNotFound() throws Exception {
        String url = "/api/order/estimated-price";

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(incomingBookOrderDTOList))
                .characterEncoding("utf-8")
        ).andExpect(status().isNotFound());
    }

    @Test
    void givenKnownBooks_whenCreateOrder_thenReturnJson() throws Exception {
        String url = "/api/order/";
        entityManager.persist(BookMocks.onTheRoad);
        entityManager.persist(buyer1);
        entityManager.flush();

        double finalPrice = BookMocks.onTheRoad.getPrice() * 5;
        Order order = new Order("XYZ", "Address", finalPrice, buyer1);
        BookOrder bookOrder = new BookOrder(BookMocks.onTheRoad, order, 5);
        order.addBookOrder(bookOrder);
        OrderDTO orderDTO = OrderDTO.fromOrder(order);
        orderDTO.setId(1);

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(incomingOrderDTO))
                .characterEncoding("utf-8")
        ).andExpect(status().isCreated())
           .andExpect(content().json(toJson(orderDTO)));
    }

    @Test
    void givenBookNotFoundException_whenCreateOrder_thenThrowHTTPStatusNotFound() throws Exception {
        String url = "/api/order/";

        entityManager.persist(buyer1);
        entityManager.flush();

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(incomingOrderDTO))
                .characterEncoding("utf-8")
        ).andExpect(status().isNotFound());
    }

    @Test
    void givenUserNotFoundException_whenCreateOrder_thenThrowHTTPStatusNotFound() throws Exception {
        String url = "/api/order/";

        entityManager.persist(BookMocks.onTheRoad);
        entityManager.flush();

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(incomingOrderDTO))
                .characterEncoding("utf-8")
        ).andExpect(status().isNotFound());
    }

    @Test
    void givenRepeatedPaymentReferenceException_whenCreateOrder_thenThrowHTTPStatusBadRequest() throws Exception {
        String url = "/api/order/";

        IncomingOrderDTO repeatedPaymentOrder = new IncomingOrderDTO(incomingBookOrderDTOList, BuyerMock.buyer1.getUsername(),
                "AC%EWRGER684654165", "Address");
        entityManager.persist(BookMocks.onTheRoad);
        entityManager.persist(order);
        entityManager.flush();

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(repeatedPaymentOrder))
                .characterEncoding("utf-8")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void givenNotEnoughStockException_whenCreateOrder_thenThrowHTTPStatusBadRequest() throws Exception {
        String url = "/api/order/";

        IncomingBookOrderDTO hugeIncomingOrder = new IncomingBookOrderDTO(BookMocks.onTheRoad.getIsbn(),
                250);
        List<IncomingBookOrderDTO> incomingBookOrderDTOList = new ArrayList(Arrays.asList(hugeIncomingOrder));
        IncomingOrderDTO incomingOrderDTO = new IncomingOrderDTO(incomingBookOrderDTOList, BuyerMock.buyer1.getUsername(),
                "XYZ", "Address");

        entityManager.persist(BookMocks.onTheRoad);
        entityManager.persist(buyer1);
        entityManager.flush();

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(incomingOrderDTO))
                .characterEncoding("utf-8")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void givenEmptyIncomingOrderException_whenCreateOrder_thenThrowHTTPStatusBadRequest() throws Exception {
        String url = "/api/order/";

        IncomingOrderDTO emptyOrder = new IncomingOrderDTO(new ArrayList<>(), BuyerMock.buyer1.getUsername(),
                "XYZ", "Address");

        entityManager.persist(BookMocks.onTheRoad);
        entityManager.persist(buyer1);
        entityManager.flush();

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(emptyOrder))
                .characterEncoding("utf-8")
        ).andExpect(status().isBadRequest());
    }
}
