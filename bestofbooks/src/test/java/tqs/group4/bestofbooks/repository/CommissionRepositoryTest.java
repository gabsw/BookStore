package tqs.group4.bestofbooks.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tqs.group4.bestofbooks.model.Commission;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommissionRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CommissionRepository commissionRepository;

    @BeforeEach
    public void before() {
        entityManager.createNativeQuery("TRUNCATE books, orders, commissions, admin, books_orders, revenues").executeUpdate();
    }

    @Test
    void givenCommissions_recieveTotalAmount() {
        Commission commission =  new Commission(120.1, 40);
        Commission commission2 =  new Commission(121.1, 41);

        entityManager.persist(commission);
        entityManager.persist(commission2);

        entityManager.flush();
        Double result = commissionRepository.totalAmount();
        assertEquals(commission.getAmount() + commission2.getAmount(), result);
        entityManager.remove(commission);
        entityManager.remove(commission2);

        entityManager.flush();

    }


}