package tqs.group4.bestofbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tqs.group4.bestofbooks.dto.RevenueDTO;
import tqs.group4.bestofbooks.repository.RevenueRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class RevenueService {
    @Autowired
    RevenueRepository revenueRepository;

    public Page<RevenueDTO> getRevenuesByPublisher(String publisherName, Pageable pageable) {
        return revenueRepository.findByPublisherName(publisherName, pageable).map(RevenueDTO::fromRevenue);
    }

    public Double getRevenuesTotalByPublisher(String publisherName) {
        return revenueRepository.totalSalesAmountByPublisher(publisherName);
    }
}
