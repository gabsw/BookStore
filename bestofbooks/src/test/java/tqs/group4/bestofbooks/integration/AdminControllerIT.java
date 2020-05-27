package tqs.group4.bestofbooks.integration;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.group4.bestofbooks.BestofbooksApplication;
import tqs.group4.bestofbooks.mocks.BookMocks;
import tqs.group4.bestofbooks.mocks.CommissionMocks;
import tqs.group4.bestofbooks.model.Book;
import tqs.group4.bestofbooks.model.Commission;
import tqs.group4.bestofbooks.model.Order;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tqs.group4.bestofbooks.mocks.BuyerMock.buyer1;
import static tqs.group4.bestofbooks.utils.Json.toJson;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = BestofbooksApplication.class)
@AutoConfigureMockMvc
@Transactional
public class AdminControllerIT {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private EntityManager entityManager;

    private Commission commission1;
    private Commission commission2;
    private Order order1;
    private Order order2;

    @BeforeEach
    public void before() {
        entityManager.createNativeQuery("TRUNCATE books, orders, commissions, admin, books_orders, revenues").executeUpdate();
        entityManager.createNativeQuery("ALTER SEQUENCE orders_id_seq RESTART WITH 1").executeUpdate();
        order1 = new Order("AC%EWRGER684654165",
                "77th st no 21, LA, CA, USA",
                10.00,
                buyer1);
        order2 = new Order("AC%EWRGER684654164",
                "77th st no 21, LA, CA, USA",
                20.00,
                buyer1);
        commission1 = new Commission(2.00, 1);
        commission2 = new Commission(4.00, 2);
    }

    @Test
    void givenCommissions_thenGetCommissionsTotal() throws Exception {
        entityManager.persist(order1);
        entityManager.persist(order2);
        entityManager.persist(commission1);
        entityManager.persist(commission2);
        entityManager.flush();

        Double expectedTotal = commission1.getAmount() + commission2.getAmount();
        String url = "/api/admin/commissions/total";

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status()
                .isOk())
                .andExpect(content().json(toJson(expectedTotal)));

    }

    @Test
    void givenNoCommissions_thenGetZeroAsTotal() throws Exception {
        Double expectedTotal = 0.00;
        String url = "/api/admin/commissions/total";
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status()
                .isOk())
                .andExpect(content().json(toJson(expectedTotal)));
    }

    @Test
    void givenCommissions_thenGetAllCommissions() throws Exception {
        entityManager.persist(order1);
        entityManager.persist(order2);
        entityManager.persist(commission1);
        entityManager.persist(commission2);
        entityManager.flush();

        Pageable p = PageRequest.of(0, 20);
        Page<Commission> commissionPage = new PageImpl<>(Lists.newArrayList(commission1, commission2), p, 2);

        String url = "/api/admin/commissions/";

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status()
                .isOk())
           .andExpect(content().json(toJson(commissionPage)));
    }
}
