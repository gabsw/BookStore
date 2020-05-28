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
import tqs.group4.bestofbooks.mocks.BookMocks;
import tqs.group4.bestofbooks.mocks.CommissionMocks;
import tqs.group4.bestofbooks.mocks.OrderMocks;
import tqs.group4.bestofbooks.model.Book;
import tqs.group4.bestofbooks.model.Commission;
import tqs.group4.bestofbooks.repository.AdminRepository;
import tqs.group4.bestofbooks.repository.CommissionRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static tqs.group4.bestofbooks.service.CommissionService.COMMISSION_PERCENTAGE;

public class CommissionServiceTests {
    @Mock
    private CommissionRepository commissionRepository;

    @InjectMocks
    private CommissionService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void whenAllCommissionsAreRequested_getCommissionsPage() {
        Pageable p = PageRequest.of(0, 20);
        Page<Commission> commissionPage = new PageImpl<>(Lists.newArrayList(CommissionMocks.commission1,
                CommissionMocks.commission2), p, 2);

        when(commissionRepository.findAll(p)).thenReturn(commissionPage);

        Page<Commission> returned = service.getCommissions(p);
        assertEquals(commissionPage, returned);
    }

    @Test
    void whenThereAreCommissionsPresent_getCommissionTotal() {
        when(commissionRepository.totalAmount()).thenReturn(500.50);
        assertEquals(500.50, service.getCommissionsTotal());
    }

    @Test
    void whenThereAreNoCommissionsPresent_getCommissionTotalIsNull() {
        when(commissionRepository.totalAmount()).thenReturn(null);
        assertNull(service.getCommissionsTotal());
    }

    @Test
    void testComputeCommissionAmountByOrderResults() {
        assertEquals(OrderMocks.order1.getFinalPrice() * COMMISSION_PERCENTAGE,
                service.computeCommissionAmountByOrder(OrderMocks.order1));
    }
}
