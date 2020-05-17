package tqs.group4.bestofbooks.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.google.common.collect.Lists;

import tqs.group4.bestofbooks.mocks.BookMocks;
import tqs.group4.bestofbooks.model.Book;
import tqs.group4.bestofbooks.repository.BookRepository;

public class StockServiceTest {

	@Mock
    private BookRepository bookRepository;
	
	@InjectMocks
    private StockService service;
	
	@BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	void givenValidPublisherName_whenGetPublisherBooks_thenReturnPageableWithBooks() {
		Pageable p = PageRequest.of(0, 20);
        Page<Book> bookPage = new PageImpl<>(Lists.newArrayList(BookMocks.infiniteJest, BookMocks.onTheRoad), p, 2);
	
        when(bookRepository.findByPublisherName("Publisher", p)).thenReturn(bookPage);
        
        Page<Book> returned = service.getPublisherBooks("Publisher", p);
        assertEquals(bookPage, returned);
	}
	
	@Test
	void givenInvalidPublisherName_whenGetPublisherBooks_thenReturnPageableWithoutBooks() {
		Pageable p = PageRequest.of(0, 20);
        Page<Book> bookPage = new PageImpl<>(Lists.newArrayList(), p, 0);
        
        when(bookRepository.findByPublisherName("Publisher", p)).thenReturn(bookPage);
        
        Page<Book> returned = service.getPublisherBooks("Publisher", p);
        assertEquals(bookPage, returned);
	}
	
	
}
