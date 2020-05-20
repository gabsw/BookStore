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
import tqs.group4.bestofbooks.dto.IncomingBookOrderDTO;
import tqs.group4.bestofbooks.exception.BookNotFoundException;
import tqs.group4.bestofbooks.exception.EmptyIncomingOrderException;
import tqs.group4.bestofbooks.exception.NotEnoughStockException;
import tqs.group4.bestofbooks.mocks.BookMocks;
import tqs.group4.bestofbooks.model.Book;
import tqs.group4.bestofbooks.repository.BookRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class BookServiceTests {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService service;

    private int quantity = 2;
    private IncomingBookOrderDTO incomingBookOrderDTO1 = new IncomingBookOrderDTO(BookMocks.onTheRoad.getIsbn(),
            quantity);
    private IncomingBookOrderDTO incomingBookOrderDTO2 = new IncomingBookOrderDTO(BookMocks.onTheRoad.getIsbn(),
            quantity + 1);
    private IncomingBookOrderDTO failedIncomingOrder1 = new IncomingBookOrderDTO("9780140042543",
            quantity);
    private IncomingBookOrderDTO giganticIncomingOrder = new IncomingBookOrderDTO(BookMocks.onTheRoad.getIsbn(),
            100);
    private List<IncomingBookOrderDTO> incomingBookOrderDTOList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        incomingBookOrderDTOList = new ArrayList<>();
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

    @Test
    void whenBookHasEnoughCopies_checkIfBookHasEnoughCopiesMustBeTrue() {
        assertTrue(service.checkIfBookHasEnoughCopies(BookMocks.onTheRoad, 20));
    }

    @Test
    void whenBookDoesNotHaveEnoughCopies_checkIfBookHasEnoughCopiesMustBeFalse() {
        assertFalse(service.checkIfBookHasEnoughCopies(BookMocks.onTheRoad, 250));
    }

    @Test
    void whenAllTheBooksAreFound_checkComputeFinalPriceFromIncomingOrderResults() throws BookNotFoundException {
        when(bookRepository.findByIsbn(incomingBookOrderDTO1.getIsbn())).thenReturn(BookMocks.onTheRoad);
        incomingBookOrderDTOList.add(incomingBookOrderDTO1);
        incomingBookOrderDTOList.add(incomingBookOrderDTO2);
        assertEquals(BookMocks.onTheRoad.getPrice() * 5,
                service.computeFinalPriceFromIncomingOrder(incomingBookOrderDTOList));

    }

    @Test
    void whenABookIsNotFound_checkComputeFinalPriceFromIncomingOrderResults() {
        String unknownIsbn = "9780140042543";
        incomingBookOrderDTOList.add(failedIncomingOrder1);
        when(bookRepository.findByIsbn(unknownIsbn)).thenReturn(null);
        assertThrows(BookNotFoundException.class,
                () -> service.computeFinalPriceFromIncomingOrder(incomingBookOrderDTOList));

    }

    @Test
    void whenAllTheBooksAreFoundInStock_retrieveBooksAndQuantitiesFromIncomingOrderDTOS() throws BookNotFoundException, NotEnoughStockException, EmptyIncomingOrderException {
        when(bookRepository.findByIsbn(incomingBookOrderDTO1.getIsbn())).thenReturn(BookMocks.onTheRoad);
        incomingBookOrderDTOList.add(incomingBookOrderDTO1);
        incomingBookOrderDTOList.add(incomingBookOrderDTO2);

        Map<Book, Integer> booksWithQuantities = new HashMap<>();
        booksWithQuantities.put(BookMocks.onTheRoad, 5);

        assertEquals(booksWithQuantities,
                service.retrieveBooksAndQuantitiesFromIncomingOrderDTOS(incomingBookOrderDTOList));

    }

    @Test
    void whenABookIsNotFound_retrieveBooksAndQuantitiesFromIncomingOrderDTOS_ThrowsBookNotFoundException() {
        String unknownIsbn = "9780140042543";
        incomingBookOrderDTOList.add(failedIncomingOrder1);
        when(bookRepository.findByIsbn(unknownIsbn)).thenReturn(null);
        assertThrows(BookNotFoundException.class,
                () -> service.retrieveBooksAndQuantitiesFromIncomingOrderDTOS(incomingBookOrderDTOList));

    }

    @Test
    void whenABooksAreNotFoundInStock_retrieveBooksAndQuantitiesFromIncomingOrderDTOS_ThrowsNotEnoughStockException() {
        when(bookRepository.findByIsbn(incomingBookOrderDTO1.getIsbn())).thenReturn(BookMocks.onTheRoad);
        incomingBookOrderDTOList.add(giganticIncomingOrder);

        assertThrows(NotEnoughStockException.class,
                () -> service.retrieveBooksAndQuantitiesFromIncomingOrderDTOS(incomingBookOrderDTOList));

    }

    @Test
    void whenABooksAreNotFoundInIncomingOrder_retrieveBooksAndQuantitiesFromIncomingOrderDTOS_ThrowsEmptyIncomingOrderException() {
        assertThrows(EmptyIncomingOrderException.class,
                () -> service.retrieveBooksAndQuantitiesFromIncomingOrderDTOS(incomingBookOrderDTOList));

    }
}
