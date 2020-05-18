package tqs.group4.bestofbooks.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import tqs.group4.bestofbooks.mocks.BookMocks;
import tqs.group4.bestofbooks.model.BookOrder;
import tqs.group4.bestofbooks.model.Order;
import tqs.group4.bestofbooks.model.Revenue;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static tqs.group4.bestofbooks.mocks.BuyerMock.buyer1;

@SpringBootTest
@Transactional
public class RevenueRepositoryIT {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private RevenueRepository repository;

    private Order order1;
    private BookOrder bookOrder1;
    private BookOrder bookOrder2;
    private Revenue revenue1;
    private Revenue revenue2;

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
    public void whenFindByExistentPublisherName_thenReturnRevenues() {
        entityManager.persist(revenue1);
        entityManager.persist(revenue2);
        entityManager.flush();

        Pageable p = PageRequest.of(0, 20);
        Page<Revenue> queryResults = repository.findByPublisherName("Publisher 1", p);

        assertAll("findByPublisherName",
                () -> assertTrue(queryResults.getContent().contains(revenue1)),
                () -> assertTrue(queryResults.getContent().contains(revenue2))
        );
    }

    @Test
    public void whenFindByUnknownPublisherName_thenReturnNull() {
        Pageable p = PageRequest.of(0, 20);
        Page<Revenue> queryResults = repository.findByPublisherName("None", p);
        assertTrue(queryResults.getContent().isEmpty());
    }

    @Test
    public void computeTotalSalesAmountByKnownPublisher() {
        entityManager.persist(revenue1);
        entityManager.persist(revenue2);
        entityManager.flush();

        Double queryResults = repository.totalSalesAmountByPublisher("Publisher 1");
        assertEquals(revenue1.getAmount() + revenue2.getAmount(), queryResults);
    }

    @Test
    public void computeTotalSalesAmountByUnKnownPublisher() {
        entityManager.persist(revenue1);
        entityManager.persist(revenue2);
        entityManager.flush();

        Double queryResults = repository.totalSalesAmountByPublisher("None");
        assertNull(queryResults);
    }
}
