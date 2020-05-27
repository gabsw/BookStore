package tqs.group4.bestofbooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tqs.group4.bestofbooks.dto.OrderDTO;
import tqs.group4.bestofbooks.exception.ForbiddenUserException;
import tqs.group4.bestofbooks.exception.LoginRequiredException;
import tqs.group4.bestofbooks.service.LoginServices;
import tqs.group4.bestofbooks.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/buyer")
public class BuyersController {
    
    @Autowired
    private OrderService orderService;

    @Autowired
    private LoginServices loginService;

    @GetMapping("/{buyerUsername}/orders")
    public List<OrderDTO> getOrdersByBuyerUsername(@PathVariable String buyerUsername, HttpServletRequest request)
            throws LoginRequiredException, ForbiddenUserException {
        loginService.checkIfUserIsTheRightBuyer(buyerUsername, loginService.getSessionUsername(request));
        return orderService.getOrderByBuyerUsername(buyerUsername);
    }
}