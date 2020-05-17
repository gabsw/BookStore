package tqs.group4.bestofbooks.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import tqs.group4.bestofbooks.dto.RequestOrderDTO;
import tqs.group4.bestofbooks.exception.OrderNotFoundException;
import tqs.group4.bestofbooks.model.Order;
import tqs.group4.bestofbooks.repository.OrderRepository;

@Service
@Transactional
public class OrderService {
    
    @Autowired
    private OrderRepository repo;

    public Order getById(int id){
        Order order = repo.findById(id);
        if (order == null)
            throw new OrderNotFoundException("No order with the given id.");
        return order;
    }

    public List<Order> getByBuyerUsername(String buyerUsername){
        List<Order> orders = repo.findByBuyerUsername(buyerUsername);
        if (orders == null)
            throw new OrderNotFoundException("No orders for the given username.");
        return orders;
    }
}