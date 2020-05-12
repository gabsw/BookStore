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
import tqs.group4.bestofbooks.exception.BookNotFoundException;
import tqs.group4.bestofbooks.mocks.BookMocks;
import tqs.group4.bestofbooks.model.Book;
import tqs.group4.bestofbooks.repository.BookRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class BookServiceTests {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void searchBookByExistentIsbn_getBook() throws BookNotFoundException {
        String existentIsbn = BookMocks.onTheRoad.getIsbn();
        when(bookRepository.findByIsbn(existentIsbn)).thenReturn(BookMocks.onTheRoad);
        assertEquals(BookMocks.onTheRoad, service.getBookByIsbn(existentIsbn));
    }

    @Test
    void searchBookByUnknownIsbn_BookNotFoundExceptionShouldBeThrown() {
        String unknownIsbn = "9780140042543";
        when(bookRepository.findByIsbn(unknownIsbn)).thenReturn(null);
        assertThrows(BookNotFoundException.class,
                () -> service.getBookByIsbn(unknownIsbn));
    }

    @Test
    void searchAvailableBooks_getBooksPage() {
        Pageable p = PageRequest.of(0, 20);
        Page<Book> bookPage = new PageImpl<>(Lists.newArrayList(BookMocks.infiniteJest, BookMocks.onTheRoad), p, 2);

        when(bookRepository.findByQuantityGreaterThan(0, p)).thenReturn(bookPage);

        Page<Book> returned = service.getAvailableBooks(p);
        assertEquals(bookPage, returned);
    }

    @Test
    void searchFilteredBooks_getBooksPage() {
        Pageable p = PageRequest.of(0, 20);
        Page<Book> bookPage = new PageImpl<>(Lists.newArrayList(BookMocks.onTheRoad), p, 1);

        when(bookRepository.search(BookMocks.onTheRoad.getTitle(), BookMocks.onTheRoad.getAuthor(),
                BookMocks.onTheRoad.getCategory(), p)).thenReturn(bookPage);

        Page<Book> returned = service.getFilteredBooks(BookMocks.onTheRoad.getTitle(), BookMocks.onTheRoad.getAuthor(),
                BookMocks.onTheRoad.getCategory(), p);
        assertEquals(bookPage, returned);
    }
}
