package tqs.group4.bestofbooks.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tqs.group4.bestofbooks.mocks.BookMocks;
import tqs.group4.bestofbooks.mocks.BookOrderMocks;
import tqs.group4.bestofbooks.mocks.OrderMocks;
import tqs.group4.bestofbooks.utils.Equals;

import static org.junit.jupiter.api.Assertions.*;

public class BookTests {
    private Book equalBook;

    @BeforeEach
    void initializeBooks() {
        equalBook = new Book("9780316921176", "Infinite Jest", "David Foster Wallace",
                "Set in an addicts' halfway house and a tennis academy", 18, 5,
                "Postmodernism", "Little, Brown");
    }

    @Test
    void checkGetIsbn() {
        assertEquals("9780316921176", BookMocks.infiniteJest.getIsbn());
    }

    @Test
    void checkGetTitle() {
        assertEquals("Infinite Jest", BookMocks.infiniteJest.getTitle());
    }

    @Test
    void checkGetAuthor() {
        assertEquals("David Foster Wallace", BookMocks.infiniteJest.getAuthor());
    }

    @Test
    void checkGetPrice() {
        assertEquals(18, BookMocks.infiniteJest.getPrice());
    }

    @Test
    void checkGetQuantity() {
        assertEquals(5, BookMocks.infiniteJest.getQuantity());
    }

    @Test
    void checkGetPublisher() {
        assertEquals("Little, Brown", BookMocks.infiniteJest.getPublisherName());
    }

    @Test
    void checkGetCategory() {
        assertEquals("Postmodernism", BookMocks.infiniteJest.getCategory());
    }
}
