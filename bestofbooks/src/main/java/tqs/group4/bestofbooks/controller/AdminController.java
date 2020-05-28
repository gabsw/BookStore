package tqs.group4.bestofbooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tqs.group4.bestofbooks.exception.ForbiddenUserException;
import tqs.group4.bestofbooks.exception.LoginRequiredException;
import tqs.group4.bestofbooks.model.Commission;
import tqs.group4.bestofbooks.service.CommissionService;
import tqs.group4.bestofbooks.service.LoginService;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/api/admin/commissions")
public class AdminController {
    @Autowired
    private CommissionService commissionService;

    @Autowired
    private LoginService loginService;

    @GetMapping("/")
    public Page<Commission> getCommissions(Pageable pageable, HttpServletRequest request)
            throws LoginRequiredException, ForbiddenUserException {
        loginService.checkUserIsAdmin(loginService.getSessionUsername(request));
        return commissionService.getCommissions(pageable);
    }

    @GetMapping("/total")
    public Double getCommissionsTotal(HttpServletRequest request)
            throws LoginRequiredException, ForbiddenUserException {
        loginService.checkUserIsAdmin(loginService.getSessionUsername(request));
        return commissionService.getCommissionsTotal();
    }
}
