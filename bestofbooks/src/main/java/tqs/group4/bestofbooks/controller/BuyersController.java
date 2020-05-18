package tqs.group4.bestofbooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tqs.group4.bestofbooks.dto.OrderDTO;
import tqs.group4.bestofbooks.service.OrderService;

import java.util.List;

@CrossOrigin
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