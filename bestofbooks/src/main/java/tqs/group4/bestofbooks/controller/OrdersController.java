package tqs.group4.bestofbooks.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tqs.group4.bestofbooks.dto.IncomingBookOrderDTO;
import tqs.group4.bestofbooks.dto.OrderDTO;
import tqs.group4.bestofbooks.dto.IncomingOrderDTO;
import tqs.group4.bestofbooks.exception.*;
import tqs.group4.bestofbooks.service.OrderService;

import javax.validation.Valid;
import java.util.List;

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

    @PostMapping("/")
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody IncomingOrderDTO incomingOrderDTO)
            throws BookNotFoundException, UserNotFoundException, NotEnoughStockException,
            RepeatedPaymentReferenceException, EmptyIncomingOrderException {
        OrderDTO createdOrder = service.createOrderDTO(incomingOrderDTO);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PostMapping("/estimated-price")
    public Double computePriceForIncomingOrder(@Valid @RequestBody List<IncomingBookOrderDTO> incomingBookOrderDTOList)
            throws BookNotFoundException {
        return service.computePriceForIncomingOrder(incomingBookOrderDTOList);
    }
}