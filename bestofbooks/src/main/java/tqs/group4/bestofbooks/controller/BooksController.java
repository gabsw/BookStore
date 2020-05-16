package tqs.group4.bestofbooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import tqs.group4.bestofbooks.exception.BookNotFoundException;
import tqs.group4.bestofbooks.model.Book;
import tqs.group4.bestofbooks.service.BookService;

@CrossOrigin
@RestController
@RequestMapping("/api/books")
public class BooksController {
    @Autowired
    private BookService bookService;

    @GetMapping("/available")
    public Page<Book> getAvailableBooks(Pageable pageable) {
        return bookService.getAvailableBooks(pageable);
    }

    @GetMapping("/isbn/{isbn:[0-9]{13}}")
    public Book getBookByIsbn(@PathVariable String isbn) throws BookNotFoundException {
        return bookService.getBookByIsbn(isbn);
    }

    @GetMapping("/search")
    public Page<Book> getBookByTitle(@RequestParam(required = false) String title,
                                     @RequestParam(required = false) String author,
                                     @RequestParam(required = false) String category,
                                     Pageable pageable) {
        return bookService.getFilteredBooks(title, author, category, pageable);
    }
}
