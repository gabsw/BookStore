package tqs.group4.bestofbooks.integration;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.group4.bestofbooks.BestofbooksApplication;
import tqs.group4.bestofbooks.dto.OrderDTO;
import tqs.group4.bestofbooks.mocks.BuyerMock;
import tqs.group4.bestofbooks.mocks.OrderMocks;
import tqs.group4.bestofbooks.model.Buyer;
import tqs.group4.bestofbooks.utils.Auth;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

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
    void givenCorrectBuyer_whenGetOrderByBuyerUsername_thenReturnJson() throws Exception {
        entityManager.persist(BuyerMock.buyer1);
        entityManager.persist(OrderMocks.order1);
        entityManager.flush();

        String token = Auth.fetchToken(mvc, BuyerMock.buyer1.getUsername(), "pw");

        String url = "/api/buyer/" + BuyerMock.buyer1.getUsername() + "/orders";

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-auth-token", token)
        ).andExpect(status()
                .isOk())
           .andExpect(content().json(toJson(orders)));
    }

    @Test
    void givenMismatchedBuyer_thenThrowHTTPForbidden() throws Exception {
        Buyer mismatch = new Buyer("buyer3", "30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4");
        entityManager.persist(mismatch);
        entityManager.persist(BuyerMock.buyer1);
        String url = "/api/buyer/" + BuyerMock.buyer1.getUsername() + "/orders";
        String token = Auth.fetchToken(mvc, mismatch.getUsername(), "pw");

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-auth-token", token)
        ).andExpect(status().isForbidden());
    }
}
