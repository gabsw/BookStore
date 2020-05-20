package tqs.group4.bestofbooks.repository;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tqs.group4.bestofbooks.model.Order;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static tqs.group4.bestofbooks.mocks.BuyerMock.buyer1;

@SpringBootTest
@Transactional
public class OrderRepositoryIT {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    OrderRepository orderRepository;

    private Order order;

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
    public void whenFindByExistentId_thenReturnOrder() {
        entityManager.persist(order);
        entityManager.flush();

        Order queryResults = orderRepository.findById(order.getId().intValue());
        assertEquals(order, queryResults);
    }

    @Test
    public void whenFindByUnknownId_thenReturnNull() {
        Order queryResults = orderRepository.findById(1994949);
        assertNull(queryResults);
    }

    @Test
    public void whenFindByExistentBuyer_thenReturnOrder() {
        entityManager.persist(order);
        entityManager.flush();

        List<Order> queryResults = orderRepository.findByBuyerUsername(order.getBuyer().getUsername());
        assertEquals(Lists.newArrayList(order), queryResults);
    }

    @Test
    public void whenFindByUnknownBuyer_thenReturnNull() {
        List<Order> queryResults = orderRepository.findByBuyerUsername("None");
        assertEquals(0, queryResults.size());
    }

    @Test
    public void whenExistsByKnownPaymentReference_thenReturnTrue() {
        entityManager.persist(order);
        entityManager.flush();
        boolean queryResults = orderRepository.existsByPaymentReference("AC%EWRGER684654165");
        assertTrue(queryResults);
    }

    @Test
    public void whenExistsByUnknownPaymentReference_thenReturnFalse() {
        boolean queryResults = orderRepository.existsByPaymentReference("None");
        assertFalse(queryResults);
    }
}