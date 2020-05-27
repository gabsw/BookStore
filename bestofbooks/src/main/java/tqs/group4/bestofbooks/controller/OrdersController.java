package tqs.group4.bestofbooks.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tqs.group4.bestofbooks.dto.IncomingBookOrderDTO;
import tqs.group4.bestofbooks.dto.OrderDTO;
import tqs.group4.bestofbooks.dto.IncomingOrderDTO;
import tqs.group4.bestofbooks.exception.*;
import tqs.group4.bestofbooks.service.LoginServices;
import tqs.group4.bestofbooks.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/order")
public class OrdersController {

    @Autowired
    private OrderService service;

    @Autowired
    private LoginServices loginService;

    @GetMapping(value="/{id}")
    public OrderDTO getOrderById(@PathVariable int id, HttpServletRequest request)
            throws LoginRequiredException, ForbiddenUserException {
        OrderDTO orderDTO = service.getOrderById(id);
        loginService.checkIfUserIsTheRightBuyerForOrder(orderDTO, loginService.getSessionUsername(request));
        return orderDTO;
    }

    @PostMapping("/")
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody IncomingOrderDTO incomingOrderDTO, HttpServletRequest request)
            throws BookNotFoundException, UserNotFoundException, NotEnoughStockException,
            RepeatedPaymentReferenceException, EmptyIncomingOrderException, LoginRequiredException, ForbiddenUserException {
        loginService.checkUserIsBuyer(loginService.getSessionUsername(request));
        OrderDTO createdOrder = service.createOrderDTO(incomingOrderDTO);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PostMapping("/estimated-price")
    public Double computePriceForIncomingOrder(@Valid @RequestBody List<IncomingBookOrderDTO> incomingBookOrderDTOList, HttpServletRequest request)
            throws BookNotFoundException, LoginRequiredException, ForbiddenUserException {
        loginService.checkUserIsBuyer(loginService.getSessionUsername(request));
        return service.computePriceForIncomingOrder(incomingBookOrderDTOList);
    }
}