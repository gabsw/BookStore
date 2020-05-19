package tqs.group4.bestofbooks.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tqs.group4.bestofbooks.dto.OrderDTO;
import tqs.group4.bestofbooks.dto.RequestOrderDTO;
import tqs.group4.bestofbooks.exception.BookNotFoundException;
import tqs.group4.bestofbooks.exception.NotEnoughStockException;
import tqs.group4.bestofbooks.exception.UserNotFoundException;
import tqs.group4.bestofbooks.service.OrderService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/order")
public class OrdersController {

    @Autowired
    private OrderService service;

    @GetMapping(value="/{id}")
    public OrderDTO getOrderById(@PathVariable int id) {
        return service.getOrderById(id);
    }

    // TODO: Check validation exception
    @PostMapping("/")
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody RequestOrderDTO requestOrderDTO)
            throws BookNotFoundException, UserNotFoundException, NotEnoughStockException {
        OrderDTO createdOrder = service.createOrderDTO(requestOrderDTO);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }
}