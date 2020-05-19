package tqs.group4.bestofbooks.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tqs.group4.bestofbooks.exception.UserNotFoundException;
import tqs.group4.bestofbooks.mocks.BuyerMock;
import tqs.group4.bestofbooks.repository.BuyerRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class UserServiceTests {
    @Mock
    private BuyerRepository buyerRepository;

    @InjectMocks
    private UserService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void searchBuyerByExistentUsername_getBuyer() throws UserNotFoundException {
        String existentUsername = BuyerMock.buyer1.getUsername();
        when(buyerRepository.findById(existentUsername)).thenReturn(java.util.Optional.of(BuyerMock.buyer1));
        assertEquals(BuyerMock.buyer1, service.getBuyerFromUsername(existentUsername));
    }

    @Test
    void searchBuyerByUnknownIsbn_UserNotFoundExceptionShouldBeThrown() {
        String unknownUsername = "None";
        assertThrows(UserNotFoundException.class,
                () -> service.getBuyerFromUsername(unknownUsername));
    }
}
