package tqs.group4.bestofbooks.model;

import org.junit.jupiter.api.Test;
import tqs.group4.bestofbooks.mocks.CommissionMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommissionTest {
    @Test
    void checkGetOrderId() {
        assertEquals(30, CommissionMocks.commission1.getOrderId());
    }

    @Test
    void checkGetAmount() {
        assertEquals(120.0, CommissionMocks.commission1.getAmount());
    }

    @Test
    void checkSetGetId() {
        // New object in order not to impact the mocks
        Commission commission = new Commission(150, 32);
        commission.setId(130);
        assertEquals(130, commission.getId());
    }
}
