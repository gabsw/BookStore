package tqs.group4.bestofbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tqs.group4.bestofbooks.dto.RevenueDTO;
import tqs.group4.bestofbooks.exception.UserNotFoundException;
import tqs.group4.bestofbooks.model.BookOrder;
import tqs.group4.bestofbooks.repository.PublisherRepository;
import tqs.group4.bestofbooks.repository.RevenueRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class RevenueService {
    static final double REVENUE_PERCENTAGE = 0.80;

    @Autowired
    private RevenueRepository revenueRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    public Page<RevenueDTO> getRevenuesByPublisher(String publisherName, Pageable pageable)
            throws UserNotFoundException {
        if (!publisherRepository.existsByName(publisherName)) {
            throw new UserNotFoundException("Publisher named " + publisherName + " was not found.");
        }
        return revenueRepository.findByPublisherName(publisherName, pageable).map(RevenueDTO::fromRevenue);
    }

    public Double getRevenuesTotalByPublisher(String publisherName)
            throws UserNotFoundException {
        if (!publisherRepository.existsByName(publisherName)) {
            throw new UserNotFoundException("Publisher named " + publisherName + " was not found.");
        }
        return revenueRepository.totalSalesAmountByPublisher(publisherName);
    }

    public double computeRevenueAmountByBookOrder(BookOrder bookOrder) {
        return bookOrder.getQuantity() * bookOrder.getBook().getPrice() * REVENUE_PERCENTAGE;
    }
}
