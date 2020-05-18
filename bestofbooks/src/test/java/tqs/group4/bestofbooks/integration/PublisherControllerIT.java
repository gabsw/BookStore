package tqs.group4.bestofbooks.integration;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import tqs.group4.bestofbooks.dto.RevenueDTO;
import tqs.group4.bestofbooks.mocks.BookMocks;
import tqs.group4.bestofbooks.model.BookOrder;
import tqs.group4.bestofbooks.model.Order;
import tqs.group4.bestofbooks.model.Revenue;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tqs.group4.bestofbooks.mocks.BuyerMock.buyer1;
import static tqs.group4.bestofbooks.utils.Json.toJson;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = BestofbooksApplication.class)
@AutoConfigureMockMvc
@Transactional
public class PublisherControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EntityManager entityManager;

    // Need to use these instead of the Mocks due to "detached entity cannot be persisted"
    private Pageable p = PageRequest.of(0, 20);
    private Revenue revenue1;
    private Revenue revenue2;
    private BookOrder bookOrder1;
    private BookOrder bookOrder2;
    private Order order1;

    @BeforeEach
    public void before() {
        entityManager.createNativeQuery("TRUNCATE books, orders, commissions, books_orders, revenues").executeUpdate();
        order1 = new Order(
                "AC%EWRGER684654165",
                "77th st no 21, LA, CA, USA",
                10.00,
                buyer1
        );
        bookOrder1 = new BookOrder(BookMocks.onTheRoad, order1, 2);
        bookOrder2 = new BookOrder(BookMocks.infiniteJest, order1, 5);
        order1.addBookOrder(bookOrder1);
        order1.addBookOrder(bookOrder2);
        revenue1 = new Revenue(150, bookOrder1, "Publisher 1");
        revenue2 = new Revenue(300, bookOrder2, "Publisher 1");
    }

    @Test
    void givenExistentPublisherName_whenGetRevenuesByPublisherName_thenReturnJson() throws Exception {
        entityManager.persist(revenue1);
        entityManager.persist(revenue2);
        entityManager.flush();

        String knownPublisher = revenue1.getPublisherName();
        String url = "/api/publisher/" + knownPublisher + "/revenue";

        Page<RevenueDTO> revenueDTOPage = new PageImpl<>(
                Lists.newArrayList(RevenueDTO.fromRevenue(revenue1),
                        RevenueDTO.fromRevenue(revenue2)),
                p, 2);

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status()
                .isOk())
           .andExpect(content().json(toJson(revenueDTOPage)));
    }

    @Test
    void givenUnknownPublisherName_thenThrowHTTPStatusNotFound_forRevenues() throws Exception {
        String unknownPublisher = "none";
        String url = "/api/publisher/" + unknownPublisher + "/revenue";

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    void givenExistentPublisherName_whenGetTotalRevenuesByPublisherName_thenReturnJson() throws Exception {
        entityManager.persist(revenue1);
        entityManager.persist(revenue2);
        entityManager.flush();

        String knownPublisher = revenue1.getPublisherName();
        double totalRevenue = revenue1.getAmount() + revenue2.getAmount();
        String url = "/api/publisher/" + knownPublisher + "/revenue/total";

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status()
                .isOk())
           .andExpect(content().json(toJson(totalRevenue)));
    }

    @Test
    void givenUnknownPublisherName_thenThrowHTTPStatusNotFound_forTotalInRevenues() throws Exception {
        String unknownPublisher = "none";
        String url = "/api/publisher/" + unknownPublisher + "/revenue/total";

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }
}
