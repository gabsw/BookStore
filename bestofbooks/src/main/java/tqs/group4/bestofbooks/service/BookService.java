package tqs.group4.bestofbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.group4.bestofbooks.dto.IncomingBookOrderDTO;
import tqs.group4.bestofbooks.exception.BookNotFoundException;
import tqs.group4.bestofbooks.exception.EmptyIncomingOrderException;
import tqs.group4.bestofbooks.exception.NotEnoughStockException;
import tqs.group4.bestofbooks.model.Book;
import tqs.group4.bestofbooks.repository.BookRepository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
            throw new BookNotFoundException(isbn);
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
                throw new BookNotFoundException(incomingOrder.getIsbn());
            } else {
                finalPrice += (book.getPrice() * incomingOrder.getQuantity());
            }
        }
        return finalPrice;
    }

    public Map<Book, Integer> retrieveBooksAndQuantitiesFromIncomingOrderDTOS(List<IncomingBookOrderDTO>
                                                                                      incomingBookOrderDTOS)
            throws BookNotFoundException, NotEnoughStockException, EmptyIncomingOrderException {

        if (incomingBookOrderDTOS.isEmpty()) {
            throw new EmptyIncomingOrderException("Incoming order needs to hold books, cannot be empty.");
        }

        Map<Book, Integer> booksWithQuantities = new HashMap<>();
        for (IncomingBookOrderDTO incomingOrder : incomingBookOrderDTOS) {
            Book book = bookRepository.findByIsbn(incomingOrder.getIsbn());
            int quantityInRequest = incomingOrder.getQuantity();
            if (book == null) {
                throw new BookNotFoundException(incomingOrder.getIsbn());
            } else if (!checkIfBookHasEnoughCopies(book, quantityInRequest)) {
                throw new NotEnoughStockException(book.getTitle() + " does not have " +
                        "enough copies in stock to fulfill order request.");
            } else {
                if (booksWithQuantities.containsKey(book)) {
                    int previousQuantity = booksWithQuantities.get(book);
                    booksWithQuantities.replace(book, previousQuantity + quantityInRequest);
                } else {
                    booksWithQuantities.put(book, quantityInRequest);
                }

            }
        }
        return booksWithQuantities;
    }

    public boolean checkIfBookHasEnoughCopies(Book book, int quantity) {
        return book.getQuantity() >= quantity;
    }
}
