package tqs.group4.bestofbooks.controller;

//import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tqs.group4.bestofbooks.exception.OrderNotFoundException;
//import tqs.group4.bestofbooks.dto.RequestOrderDTO;
import tqs.group4.bestofbooks.model.Order;
import tqs.group4.bestofbooks.service.OrderService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/order")
public class OrdersController {

    @Autowired
    private OrderService service;

    @GetMapping(value="/id/{id}")
    public Order getById(@PathVariable int id) throws OrderNotFoundException {
        return service.getById(id);
    }
    /*
    @PostMapping(value="/")
    public ResponseEntity<Order> addMultipleOrders(@Valid @RequestBody RequestOrderDTO requestOrderDTO){
        return new ResponseEntity<>(service.insert(requestOrderDTO), HttpStatus.CREATED);
    }
    */
}