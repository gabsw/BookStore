package tqs.group4.bestofbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tqs.group4.bestofbooks.model.Commission;
import tqs.group4.bestofbooks.repository.CommissionRepository;

@Service
public class CommissionService {
    @Autowired
    private CommissionRepository commissionRepository;

    public Double getCommissionsTotal() {
        return commissionRepository.totalAmount();
    }

    public Page<Commission> getCommissions(Pageable pageable) {
        return commissionRepository.findAll(pageable);
    }
}
