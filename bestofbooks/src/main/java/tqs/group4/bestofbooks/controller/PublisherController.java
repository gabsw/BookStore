package tqs.group4.bestofbooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import tqs.group4.bestofbooks.dto.RevenueDTO;
import tqs.group4.bestofbooks.exception.UserNotFoundException;
import tqs.group4.bestofbooks.service.RevenueService;

@CrossOrigin
@RestController
@RequestMapping("/api/publisher")
public class PublisherController {
    @Autowired
    private RevenueService revenueService;

    @GetMapping("/{publisherName}/revenue")
    public Page<RevenueDTO> getRevenuesByPublisher(@PathVariable String publisherName, Pageable pageable)
            throws UserNotFoundException {
        return revenueService.getRevenuesByPublisher(publisherName, pageable);
    }

    @GetMapping("/{publisherName}/revenue/total")
    public Double getRevenuesTotalByPublisher(@PathVariable String publisherName)
            throws UserNotFoundException {
        return revenueService.getRevenuesTotalByPublisher(publisherName);
    }
}
