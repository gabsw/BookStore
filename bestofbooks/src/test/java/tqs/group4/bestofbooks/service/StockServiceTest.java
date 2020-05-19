package tqs.group4.bestofbooks.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.mockito.ArgumentMatchers.any;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpServletRequest;

import com.google.common.collect.Lists;

import tqs.group4.bestofbooks.dto.StockDto;
import tqs.group4.bestofbooks.dto.UserDto;
import tqs.group4.bestofbooks.exception.BookNotFoundException;
import tqs.group4.bestofbooks.exception.ForbiddenUserException;
import tqs.group4.bestofbooks.exception.LoginFailedException;
import tqs.group4.bestofbooks.exception.LoginRequiredException;
import tqs.group4.bestofbooks.exception.UserNotFoundException;
import tqs.group4.bestofbooks.mocks.BookMocks;
import tqs.group4.bestofbooks.model.Book;
import tqs.group4.bestofbooks.repository.BookRepository;

public class StockServiceTest {

	@Mock
    private BookRepository bookRepository;
	
	@Mock
	private LoginServices loginService;
	
	@InjectMocks
    private StockService service;
	
	@BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	void givenValidPublisherTokenWithBooks_whenGetPublisherBooks_thenReturnPageableWithBooks() throws UserNotFoundException, LoginRequiredException, ForbiddenUserException {
		Pageable p = PageRequest.of(0, 20);
        Page<Book> bookPage = new PageImpl<>(Lists.newArrayList(BookMocks.infiniteJest, BookMocks.onTheRoad), p, 2);
        UserDto dto = new UserDto("username", "Publisher");
		dto.addAttribute("name", "Publisher");
		dto.addAttribute("tin", "PublisherTIN");
        when(bookRepository.findByPublisherName("Publisher", p)).thenReturn(bookPage);
        when(loginService.getSessionUsername(any(HttpServletRequest.class))).thenReturn("publisher");
        when(loginService.getUserDtoByUsername("publisher")).thenReturn(dto);
        
        Page<Book> returned = service.getPublisherBooks("Publisher",new MockHttpServletRequest(), p);
        assertEquals(bookPage, returned);
	}
	
	@Test
	void givenValidPublisherTokenWithoutBooks_whenGetPublisherBooks_thenReturnPageableWithoutBooks() throws UserNotFoundException, LoginRequiredException, ForbiddenUserException {
		Pageable p = PageRequest.of(0, 20);
        Page<Book> bookPage = new PageImpl<>(Lists.newArrayList(), p, 0);
        UserDto dto = new UserDto("username", "Publisher");
		dto.addAttribute("name", "Publisher");
		dto.addAttribute("tin", "PublisherTIN");
        when(bookRepository.findByPublisherName("Publisher", p)).thenReturn(bookPage);
        when(loginService.getSessionUsername(any(HttpServletRequest.class))).thenReturn("publisher");
        when(loginService.getUserDtoByUsername("publisher")).thenReturn(dto);
        
        Page<Book> returned = service.getPublisherBooks("Publisher" ,new MockHttpServletRequest(), p);
        assertEquals(bookPage, returned);
	}
	
	@Test
	void givenValidNonPublisherToken_whenGetPublisherBooks_thenThrowForbiddenUserException() throws LoginRequiredException, ForbiddenUserException {
		Pageable p = PageRequest.of(0, 20);
		when(loginService.getSessionUsername(any(HttpServletRequest.class))).thenReturn("publisher");
		doThrow(new ForbiddenUserException("User not allowed.")).when(loginService).checkUserIsPublisher("publisher");
		
		assertThrows(ForbiddenUserException.class,
                () -> service.getPublisherBooks("Publisher",new MockHttpServletRequest(), p));
	}
	
	@Test
	void givenInvalidPublisherToken_whenGetPublisherBooks_thenThrowLoginRequiredException() throws LoginRequiredException, ForbiddenUserException {
		Pageable p = PageRequest.of(0, 20);
		when(loginService.getSessionUsername(any(HttpServletRequest.class))).thenReturn("publisher");
		doThrow(new LoginRequiredException("Login required for this request.")).when(loginService).checkUserIsPublisher("publisher");
		
		assertThrows(LoginRequiredException.class,
                () -> service.getPublisherBooks("Publisher", new MockHttpServletRequest(), p));
	}
	
	@Test
	void givenMismatchedPublisherTokenAndName_whenGetPublisherBooks_thenThrowForbiddenUserException() throws UserNotFoundException, LoginRequiredException, ForbiddenUserException {
		Pageable p = PageRequest.of(0, 20);
        UserDto dto = new UserDto("username", "Publisher");
		dto.addAttribute("name", "Publisher");
		dto.addAttribute("tin", "PublisherTIN");
        when(loginService.getSessionUsername(any(HttpServletRequest.class))).thenReturn("publisher");
        when(loginService.getUserDtoByUsername("publisher")).thenReturn(dto);
        
        assertThrows(ForbiddenUserException.class,
                () -> service.getPublisherBooks("PublisherAnotherName", new MockHttpServletRequest(), p));
	}
	
	@Test
	void givenValidPublisherTokenAndValidBookISBN_whenUpdateBookStock_thenReturnStockDtoWithNewStock() throws UserNotFoundException, LoginRequiredException, ForbiddenUserException, BookNotFoundException {
		Book b = new Book("1234567891234", "Title 1", "Author 1", "Description 1", 20, 5,
                "Travelogue", "Publisher");
		UserDto dto = new UserDto("username", "Publisher");
		dto.addAttribute("name", "Publisher");
		dto.addAttribute("tin", "PublisherTIN");
		StockDto inStock = new StockDto("1234567891234", 5);
		
        when(bookRepository.findByIsbn("1234567891234")).thenReturn(b);
        when(loginService.getSessionUsername(any(HttpServletRequest.class))).thenReturn("publisher");
        when(loginService.getUserDtoByUsername("publisher")).thenReturn(dto);
        
        StockDto returned = service.updateBookStock("Publisher", inStock, new MockHttpServletRequest());
        assertEquals(returned.getIsbn(), inStock.getIsbn());
        assertEquals(10, returned.getQuantity());
	}
	
	@Test
	void givenValidNonPublisherToken_whenUpdateBookStock_thenThrowForbiddenUserException() throws LoginRequiredException, ForbiddenUserException {
		when(loginService.getSessionUsername(any(HttpServletRequest.class))).thenReturn("publisher");
		doThrow(new ForbiddenUserException("User not allowed.")).when(loginService).checkUserIsPublisher("publisher");
		
		assertThrows(ForbiddenUserException.class,
                () -> service.updateBookStock("Publisher", new StockDto("1234567894562", 5), new MockHttpServletRequest()));
	}
	
	@Test
	void givenMismatchedPublisherTokenAndName_whenUpdateBookStock_thenThrowForbiddenUserException() throws UserNotFoundException, LoginRequiredException, ForbiddenUserException {
        UserDto dto = new UserDto("username", "Publisher");
		dto.addAttribute("name", "Publisher");
		dto.addAttribute("tin", "PublisherTIN");
        when(loginService.getSessionUsername(any(HttpServletRequest.class))).thenReturn("publisher");
        when(loginService.getUserDtoByUsername("publisher")).thenReturn(dto);
        
        assertThrows(ForbiddenUserException.class,
                () -> service.updateBookStock("PublisherAnotherName", new StockDto("1234567894521", 5),new MockHttpServletRequest()));
	}
	
	@Test
	void givenValidPublisherTokenAndInexistentBookISBN_whenUpdateBookStock_thenThrowBookNotFoundException() throws UserNotFoundException, LoginRequiredException, ForbiddenUserException {
        UserDto dto = new UserDto("username", "Publisher");
		dto.addAttribute("name", "Publisher");
		dto.addAttribute("tin", "PublisherTIN");
		StockDto inStock = new StockDto("1234567891234", 5);
        when(loginService.getSessionUsername(any(HttpServletRequest.class))).thenReturn("publisher");
        when(loginService.getUserDtoByUsername("publisher")).thenReturn(dto);
        when(bookRepository.findByIsbn("1234567891234")).thenReturn(null);
        
        assertThrows(BookNotFoundException.class,
                () -> service.updateBookStock("Publisher", inStock,new MockHttpServletRequest()));
	}
	
	@Test
	void givenValidPublisherTokenAndBookISBNOfAnotherPublisher_whenUpdateBookStock_thenThrowForbiddenUserException() throws UserNotFoundException, LoginRequiredException, ForbiddenUserException {
        UserDto dto = new UserDto("username", "Publisher");
		dto.addAttribute("name", "Publisher");
		dto.addAttribute("tin", "PublisherTIN");
		Book b = new Book("1234567891234", "Title 1", "Author 1", "Description 1", 20, 5,
                "Travelogue", "AnotherPublisher");
		StockDto inStock = new StockDto("1234567891234", 5);
        when(loginService.getSessionUsername(any(HttpServletRequest.class))).thenReturn("publisher");
        when(loginService.getUserDtoByUsername("publisher")).thenReturn(dto);
        when(bookRepository.findByIsbn("1234567891234")).thenReturn(b);
        
        assertThrows(ForbiddenUserException.class,
                () -> service.updateBookStock("Publisher", inStock,new MockHttpServletRequest()));
	}
	
	@Test
	void givenInvalidPublisherToken_whenUpdateBookStock_thenThrowLoginRequiredException() throws LoginRequiredException, ForbiddenUserException {
		when(loginService.getSessionUsername(any(HttpServletRequest.class))).thenReturn("publisher");
		doThrow(new LoginRequiredException("Login required for this request.")).when(loginService).checkUserIsPublisher("publisher");
		
		assertThrows(LoginRequiredException.class,
                () -> service.updateBookStock("Publisher", new StockDto("1234567891234", 5), new MockHttpServletRequest()));
	}
	
}
