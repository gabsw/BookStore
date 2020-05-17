package tqs.group4.bestofbooks.integration;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.group4.bestofbooks.BestofbooksApplication;
import tqs.group4.bestofbooks.dto.OrderDTO;
import tqs.group4.bestofbooks.exception.OrderNotFoundException;
import tqs.group4.bestofbooks.mocks.BuyerMock;
import tqs.group4.bestofbooks.mocks.OrderMocks;
import tqs.group4.bestofbooks.model.Buyer;
import tqs.group4.bestofbooks.model.Order;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tqs.group4.bestofbooks.utils.Json.toJson;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = BestofbooksApplication.class)
@AutoConfigureMockMvc
@Transactional
public class BuyerControllerIT {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private EntityManager entityManager;

    private List<OrderDTO> orders = Lists.newArrayList(OrderDTO.fromOrder(OrderMocks.order1));

    @BeforeEach
    public void before() {
        entityManager.createNativeQuery("TRUNCATE books, orders, commissions, books_orders, revenues, buyers").executeUpdate();
        entityManager.createNativeQuery("ALTER SEQUENCE orders_id_seq RESTART WITH 1").executeUpdate();
        orders.get(0).setId(1);
    }

    @AfterEach
    public void after() {
        entityManager.remove(BuyerMock.buyer1);
        entityManager.flush();
    }

    @Test
    void givenExistentBuyer_whenGetOrderByBuyerUsername_thenReturnJson() throws Exception {
        entityManager.persist(OrderMocks.order1);
        entityManager.flush();

        String url = "/api/buyer/" + BuyerMock.buyer1.getUsername() + "/orders";

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status()
                .isOk())
           .andExpect(content().json(toJson(orders)));
    }

    @Test
    void givenUnknownBuyer_thenThrowHTTPStatusNotFound() throws Exception {
        String unknownBuyerUsername = "None";
        String url = "/api/buyer/" + unknownBuyerUsername + "/orders";

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }
}
