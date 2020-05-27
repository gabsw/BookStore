package tqs.group4.bestofbooks.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tqs.group4.bestofbooks.dto.BookDTO;
import tqs.group4.bestofbooks.dto.BookDTOList;
import tqs.group4.bestofbooks.dto.StockDto;
import tqs.group4.bestofbooks.dto.UserDto;
import tqs.group4.bestofbooks.exception.BookNotFoundException;
import tqs.group4.bestofbooks.exception.ForbiddenUserException;
import tqs.group4.bestofbooks.exception.LoginRequiredException;
import tqs.group4.bestofbooks.exception.RepeatedBookIsbnException;
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
	
	private static String forbiddenUserMessage = "User not allowed.";
	private static String repeatedIsbnMessage = "Tried to add book with repeated isbn.";
	
	public Page<Book> getPublisherBooks(String publisherName, HttpServletRequest request, Pageable pageable) throws UserNotFoundException, LoginRequiredException, ForbiddenUserException{
		String username = loginService.getSessionUsername(request);
		loginService.checkUserIsPublisher(username);
        UserDto dto = loginService.getUserDtoByUsername(username);
        
        if(!dto.getAttributes().get("name").equals(publisherName)) {
        	throw new ForbiddenUserException(forbiddenUserMessage);
        }

		return bookRepository.findByPublisherName(dto.getAttributes().get("name"), pageable);
	}


	public StockDto updateBookStock(String publisherName, @Valid StockDto stockDto, HttpServletRequest request) throws LoginRequiredException, ForbiddenUserException, UserNotFoundException, BookNotFoundException {
		String username = loginService.getSessionUsername(request);
		loginService.checkUserIsPublisher(username);
        UserDto dto = loginService.getUserDtoByUsername(username);
        
        if(!dto.getAttributes().get("name").equals(publisherName)) {
        	throw new ForbiddenUserException(forbiddenUserMessage);
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
	
	public BookDTOList addNewBook(String publisherName, List<BookDTO> newBooks, HttpServletRequest request) throws LoginRequiredException, ForbiddenUserException, UserNotFoundException, RepeatedBookIsbnException {
		String username = loginService.getSessionUsername(request);
		loginService.checkUserIsPublisher(username);
        UserDto dto = loginService.getUserDtoByUsername(username);
        
        if(!dto.getAttributes().get("name").equals(publisherName)) {
        	throw new ForbiddenUserException(forbiddenUserMessage);
        }
		
       for (BookDTO b:newBooks) {
    	   if (bookRepository.existsByIsbn(b.getIsbn())) {
    		   throw new RepeatedBookIsbnException(repeatedIsbnMessage);
    	   }
    	   for (BookDTO b2:newBooks) {
    		   if (b != b2 && b.getIsbn().equals(b2.getIsbn())) {
    			   throw new RepeatedBookIsbnException(repeatedIsbnMessage);
    		   }
    	   }
       }
       
       for (BookDTO b:newBooks) {
    	   bookRepository.save(b.getBookObject(publisherName));
       }
		
       return new BookDTOList(newBooks);
	}
	
}
