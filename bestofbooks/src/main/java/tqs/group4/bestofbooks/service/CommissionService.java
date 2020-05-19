package tqs.group4.bestofbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tqs.group4.bestofbooks.model.Commission;
import tqs.group4.bestofbooks.model.Order;
import tqs.group4.bestofbooks.repository.CommissionRepository;

import static tqs.group4.bestofbooks.service.RevenueService.REVENUE_PERCENTAGE;

@Service
public class CommissionService {
    static final double COMMISSION_PERCENTAGE = 1.00 - REVENUE_PERCENTAGE;

    @Autowired
    private CommissionRepository commissionRepository;

    public Double getCommissionsTotal() {
        return commissionRepository.totalAmount();
    }

    public Page<Commission> getCommissions(Pageable pageable) {
        return commissionRepository.findAll(pageable);
    }

    public double computeCommissionAmountByOrder(Order order) {
        return order.getFinalPrice() * COMMISSION_PERCENTAGE;
    }
}
