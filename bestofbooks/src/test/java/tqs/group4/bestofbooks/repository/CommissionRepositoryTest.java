package tqs.group4.bestofbooks.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import tqs.group4.bestofbooks.model.Commission;
import tqs.group4.bestofbooks.model.Order;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static tqs.group4.bestofbooks.mocks.BuyerMock.buyer1;

@SpringBootTest
@Transactional
class CommissionRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CommissionRepository commissionRepository;

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
    void givenCommissions_receiveTotalAmount() {
        entityManager.persist(order1);
        entityManager.persist(order2);
        entityManager.persist(commission1);
        entityManager.persist(commission2);
        entityManager.flush();

        Double queryResult = commissionRepository.totalAmount();
        assertEquals(commission1.getAmount() + commission2.getAmount(), queryResult);
    }

    @Test
    void givenNoCommissions_receiveTotalAmountAsZero() {
        Double queryResult = commissionRepository.totalAmount();
        assertEquals(0.00, queryResult);
    }

    @Test
    void givenCommissions_fetchCommissionsPage() {
        entityManager.persist(order1);
        entityManager.persist(order2);
        entityManager.persist(commission1);
        entityManager.persist(commission2);
        entityManager.flush();

        Pageable p = PageRequest.of(0, 20);

        Page<Commission> queryResults = commissionRepository.findAll(p);
        assertAll("findAllCommissions",
                () -> assertTrue(queryResults.getContent().contains(commission1)),
                () -> assertTrue(queryResults.getContent().contains(commission2))
        );
    }
}