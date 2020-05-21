package tqs.group4.bestofbooks.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tqs.group4.bestofbooks.mocks.OrderMocks;
import tqs.group4.bestofbooks.repository.CommissionRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    // Still missing 2 feature tests

    @Test
    void testComputeCommissionAmountByOrderResults() {
        assertEquals(OrderMocks.order1.getFinalPrice() * COMMISSION_PERCENTAGE,
                service.computeCommissionAmountByOrder(OrderMocks.order1));
    }
}
