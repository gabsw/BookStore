package tqs.group4.bestofbooks.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import tqs.group4.bestofbooks.model.BookOrder;
import tqs.group4.bestofbooks.model.Order;

@SpringBootTest
@Transactional
public class OrderRepositoryIT {
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    OrderRepository orderRepository;

    List<BookOrder> bookOrders1, bookOrders2;
    List<Order> orders;
    Order order1, order2;

    BookOrder bookOrder1, bookOrder2;

    String username = "someUser", invalidUsername = "unknown";

    @BeforeEach
    public void setUp(){
        bookOrder1 = new BookOrder();
        bookOrder2 = new BookOrder();
        bookOrders1 = new ArrayList<>();
        bookOrders1.add(bookOrder1);
        bookOrders2 = new ArrayList<>();
        bookOrders2.add(bookOrder2);
        order1 = new Order("AC%EWRGER684654165", "someUser", bookOrders1, "77th st no 21, LA, CA, USA", 10.00);
        order1.setId(10);
        bookOrder1.setOrder(order1);
        order2 = new Order("AC%EWRGER684989898", "someUser", bookOrders2, "77th st no 21, LA, CA, USA", 10.00);
        order2.setId(10);
        bookOrder2.setOrder(order2);
        for (BookOrder bookOrder: bookOrders1)
            bookOrder.setOrder(order1);
        for (BookOrder bookOrder: bookOrders2)
            bookOrder.setOrder(order2);
        orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        entityManager.createNativeQuery("TRUNCATE Books, Orders, Commissions, books_orders").executeUpdate();
    }

    @AfterEach
    public void after(){
        entityManager.remove(order1);
        entityManager.remove(order2);
        entityManager.flush();
    }

    @Test
    public void whenFindByValidId_thenReturnOrder(){
        entityManager.persist(bookOrder1);
        entityManager.persist(bookOrder2);
        entityManager.persist(order1);
        entityManager.flush();

        //Order queryResults = orderRepository.findById(order1.getId());
        //assertEquals(order1, queryResults);
    }
}