package tqs.group4.bestofbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.group4.bestofbooks.dto.IncomingBookOrderDTO;
import tqs.group4.bestofbooks.exception.BookNotFoundException;
import tqs.group4.bestofbooks.model.Book;
import tqs.group4.bestofbooks.repository.BookRepository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Page<Book> getAvailableBooks(Pageable pageable) {
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

    public double computeFinalPriceFromIncomingOrder(List<IncomingBookOrderDTO> incomingBookOrderDTOS)
            throws BookNotFoundException {
        double finalPrice = 0.0;
        for (IncomingBookOrderDTO incomingOrder : incomingBookOrderDTOS) {
            Book book = bookRepository.findByIsbn(incomingOrder.getIsbn());
            if (book == null) {
                throw new BookNotFoundException("Book with " + incomingOrder.getIsbn() +
                        " was not found in the platform.");
            } else {
                finalPrice += (book.getPrice() * incomingOrder.getQuantity());
            }
        }
        return finalPrice;
    }

    public Map<Book, Integer> retrieveBooksAndQuantitiesFromIncomingOrderDTOS(List<IncomingBookOrderDTO>
                                                                                      incomingBookOrderDTOS)
            throws BookNotFoundException {
        Map<Book, Integer> booksWithQuantities = new HashMap<>();
        for (IncomingBookOrderDTO incomingOrder : incomingBookOrderDTOS) {
            Book book = bookRepository.findByIsbn(incomingOrder.getIsbn());
            if (book == null) {
                throw new BookNotFoundException("Book with " + incomingOrder.getIsbn() +
                        " was not found in the platform.");
            } else {
                booksWithQuantities.put(book, incomingOrder.getQuantity());
            }
        }
        return booksWithQuantities;
    }
}
