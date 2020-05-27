package tqs.group4.bestofbooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tqs.group4.bestofbooks.model.Commission;
import tqs.group4.bestofbooks.service.CommissionService;

@CrossOrigin
@RestController
@RequestMapping("/api/admin/commissions")
public class AdminController {
    @Autowired
    private CommissionService commissionService;

    @GetMapping("/")
    public Page<Commission> getCommissions(Pageable pageable) {
        return commissionService.getCommissions(pageable);
    }

    @GetMapping("/total")
    public Double getCommissionsTotal() {
        return commissionService.getCommissionsTotal();
    }
}
