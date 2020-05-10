package tqs.group4.bestofbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.group4.bestofbooks.exception.BookNotFoundException;
import tqs.group4.bestofbooks.model.Book;
import tqs.group4.bestofbooks.repository.BookRepository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Transactional
@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Page<Book> getAvailableBooks(Pageable pageable) {
//        For testing: Pageable p = PageRequest.of(0, 10);
        return bookRepository.findByQuantityGreaterThan(0, pageable);
    }

    public Book getBookByIsbn(String isbn) throws BookNotFoundException {
        Book book = bookRepository.findByIsbn(isbn);
        if (book == null) {
            throw new BookNotFoundException("Book with " + isbn + " was not found in the platform.");
        } else {
            return book;
        }
    }

    public Page<Book> getFilteredBooks(String title, String author, String category, Pageable pageable) {
        return bookRepository.search(title, author, category, pageable);
    }
}
