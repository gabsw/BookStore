package tqs.group4.bestofbooks.service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tqs.group4.bestofbooks.dto.StockDto;
import tqs.group4.bestofbooks.dto.UserDto;
import tqs.group4.bestofbooks.exception.BookNotFoundException;
import tqs.group4.bestofbooks.exception.ForbiddenUserException;
import tqs.group4.bestofbooks.exception.LoginRequiredException;
import tqs.group4.bestofbooks.exception.UserNotFoundException;
import tqs.group4.bestofbooks.model.Book;
import tqs.group4.bestofbooks.repository.BookRepository;

@Transactional
@Service
public class StockService {

	@Autowired
    private BookRepository bookRepository;
	
	@Autowired
	private LoginServices loginService;
	
	
	public Page<Book> getPublisherBooks(String publisherName, HttpServletRequest request, Pageable pageable) throws UserNotFoundException, LoginRequiredException, ForbiddenUserException{
		String username = loginService.getSessionUsername(request);
		loginService.checkUserIsPublisher(username);
        UserDto dto = loginService.getUserDtoByUsername(username);
        
        if(!dto.getAttributes().get("name").equals(publisherName)) {
        	throw new ForbiddenUserException("User not allowed.");
        }

		return bookRepository.findByPublisherName(dto.getAttributes().get("name"), pageable);
	}


	public StockDto updateBookStock(String publisherName, @Valid StockDto stockDto, HttpServletRequest request) throws LoginRequiredException, ForbiddenUserException, UserNotFoundException, BookNotFoundException {
		String username = loginService.getSessionUsername(request);
		loginService.checkUserIsPublisher(username);
        UserDto dto = loginService.getUserDtoByUsername(username);
        
        if(!dto.getAttributes().get("name").equals(publisherName)) {
        	throw new ForbiddenUserException("User not allowed.");
        }
        
        Book book = bookRepository.findByIsbn(stockDto.getIsbn());
        
        if (book == null) {
        	throw new BookNotFoundException("Book with " + stockDto.getIsbn() + " was not found in the platform.");
        }
        else if (!book.getPublisherName().equals(publisherName)) {
        	throw new ForbiddenUserException("Can't add stock to a book that doesn't belong to the publisher "+publisherName+".");
        }
        
        Book newBook = new Book(book.getIsbn(), book.getTitle(), book.getAuthor(), book.getDescription(), book.getPrice(), book.getQuantity()+stockDto.getQuantity(),
                book.getCategory(), book.getPublisherName());
        bookRepository.save(newBook);
        
		return new StockDto(book.getIsbn(), newBook.getQuantity());
	}

	public void decreaseStockAfterSale(Book book, int quantitySold) {
		Book newBook = new Book(book.getIsbn(), book.getTitle(), book.getAuthor(), book.getDescription(),
				book.getPrice(), book.getQuantity() - quantitySold,
				book.getCategory(), book.getPublisherName());
		bookRepository.save(newBook);
	}
	
}
