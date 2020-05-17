package tqs.group4.bestofbooks.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tqs.group4.bestofbooks.dto.OrderDTO;
import tqs.group4.bestofbooks.service.OrderService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/order")
public class OrdersController {

    @Autowired
    private OrderService service;

    @GetMapping(value="/{id}")
    public OrderDTO getOrderById(@PathVariable int id) {
        return service.getOrderById(id);
    }
}