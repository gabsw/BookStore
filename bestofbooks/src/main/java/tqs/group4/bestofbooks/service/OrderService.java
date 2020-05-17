package tqs.group4.bestofbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.group4.bestofbooks.dto.OrderDTO;
import tqs.group4.bestofbooks.exception.OrderNotFoundException;
import tqs.group4.bestofbooks.model.Order;
import tqs.group4.bestofbooks.repository.OrderRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {
    
    @Autowired
    private OrderRepository repo;

    public OrderDTO getOrderById(int id){
        Order order = repo.findById(id);
        if (order == null) {
            throw new OrderNotFoundException("No order with the given id.");
        }
//        Collection<BookOrder> bookOrders = order.getBookOrders(); // loads the lazy relationship
        return OrderDTO.fromOrder(order);
    }

    public List<OrderDTO> getOrderByBuyerUsername(String buyerUsername){
        List<Order> orders = repo.findByBuyerUsername(buyerUsername);
        if (orders == null) {
            throw new OrderNotFoundException("No orders for the given username.");
        }
        return orders.stream().map(OrderDTO::fromOrder).collect(Collectors.toList());
    }
}