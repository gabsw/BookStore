package tqs.group4.bestofbooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tqs.group4.bestofbooks.dto.OrderDTO;
import tqs.group4.bestofbooks.exception.OrderNotFoundException;
import tqs.group4.bestofbooks.service.OrderService;

import java.util.List;

@RestController
@RequestMapping(value="/api/buyer")
public class BuyersController {
    
    @Autowired
    private OrderService orderService;

    @GetMapping(value="/{buyerUsername}/orders")
    public List<OrderDTO> getOrdersByBuyerUsername(@PathVariable String buyerUsername) {
        return orderService.getOrderByBuyerUsername(buyerUsername);
    }
}