package tqs.group4.bestofbooks.service;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import tqs.group4.bestofbooks.dto.RevenueDTO;
import tqs.group4.bestofbooks.exception.UserNotFoundException;
import tqs.group4.bestofbooks.mocks.BookOrderMocks;
import tqs.group4.bestofbooks.mocks.RevenueMocks;
import tqs.group4.bestofbooks.model.Revenue;
import tqs.group4.bestofbooks.repository.PublisherRepository;
import tqs.group4.bestofbooks.repository.RevenueRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static tqs.group4.bestofbooks.dto.RevenueDTO.fromRevenue;
import static tqs.group4.bestofbooks.service.RevenueService.REVENUE_PERCENTAGE;

public class RevenueServiceTests {
    @Mock
    private RevenueRepository revenueRepository;

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private RevenueService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void givenKnownPublisherName_getRevenues() throws UserNotFoundException {
        Pageable p = PageRequest.of(0, 20);
        Page<Revenue> revenuePage = new PageImpl<>(Lists.newArrayList(RevenueMocks.revenue1,
                RevenueMocks.revenue2), p, 2);
        when(publisherRepository.existsByName(RevenueMocks.revenue1.getPublisherName()))
                .thenReturn(true);
        when(revenueRepository.findByPublisherName(RevenueMocks.revenue1.getPublisherName(), p))
                .thenReturn(revenuePage);
        Page<RevenueDTO> revenueDTORetrievedByService = service.getRevenuesByPublisher(
                RevenueMocks.revenue1.getPublisherName(), p);

        assertAll("findRevenueByPublisherName",
                () -> assertTrue(revenueDTORetrievedByService.getContent().contains(fromRevenue(RevenueMocks.revenue1))),
                () -> assertTrue(revenueDTORetrievedByService.getContent().contains(fromRevenue(RevenueMocks.revenue2)))
        );
    }

    @Test
    void givenUnknownPublisherName_UserNotFoundExceptionIsThrown() {
        Pageable p = PageRequest.of(0, 20);
        when(publisherRepository.existsByName("NONE")).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> service.getRevenuesByPublisher("NONE", p));
        assertThrows(UserNotFoundException.class, () -> service.getRevenuesTotalByPublisher("NONE"));
    }

    @Test
    void givenKnownPublisherName_getTotalRevenues() throws UserNotFoundException {
        Pageable p = PageRequest.of(0, 20);
        Page<Revenue> revenuePage = new PageImpl<>(Lists.newArrayList(RevenueMocks.revenue1,
                RevenueMocks.revenue2), p, 2);
        when(publisherRepository.existsByName(RevenueMocks.revenue1.getPublisherName()))
                .thenReturn(true);
        when(revenueRepository.totalSalesAmountByPublisher(RevenueMocks.revenue1.getPublisherName()))
                .thenReturn(500.0);

        assertEquals(500.0, service.getRevenuesTotalByPublisher(RevenueMocks.revenue1.getPublisherName()));
    }

    @Test
    void testComputeRevenueAmountByBookOrderResults() {
        double bookPrice = BookOrderMocks.bookOrder1.getBook().getPrice();
        int orderQuantity = BookOrderMocks.bookOrder1.getQuantity();
        assertEquals(bookPrice * orderQuantity * REVENUE_PERCENTAGE,
                service.computeRevenueAmountByBookOrder(BookOrderMocks.bookOrder1));
    }
}
