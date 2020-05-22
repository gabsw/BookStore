package tqs.group4.bestofbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import tqs.group4.bestofbooks.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    
    Order findById(int id);

    List<Order> findByBuyerUsername(String buyerUsername);

    boolean existsByPaymentReference(String paymentReference);
}