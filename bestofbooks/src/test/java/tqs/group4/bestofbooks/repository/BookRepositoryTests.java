package tqs.group4.bestofbooks.repository;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import tqs.group4.bestofbooks.mocks.BookMocks;
import tqs.group4.bestofbooks.model.Book;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase
@DataJpaTest
public class BookRepositoryTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void whenFindByExistentIsbn_thenReturnBook() {
        entityManager.persistAndFlush(BookMocks.onTheRoad);

        Book queryResults = bookRepository.findByIsbn(BookMocks.onTheRoad.getIsbn());
        assertEquals(BookMocks.onTheRoad, queryResults);
    }

    @Test
    public void whenFindByUnknownIsbn_thenReturnNull() {
        Book queryResults = bookRepository.findByIsbn("1234567891111");
        assertNull(queryResults);
    }

    @Test
    public void whenFindByQuantityGreaterThan_thenReturnPageOfBook() {
        entityManager.persistAndFlush(BookMocks.onTheRoad);
        entityManager.persistAndFlush(BookMocks.infiniteJest);

        Pageable p = PageRequest.of(0, 20);
        Page<Book> bookPage = new PageImpl<>(Lists.newArrayList(BookMocks.infiniteJest, BookMocks.onTheRoad), p, 2);

        Page<Book> queryResults = bookRepository.findByQuantityGreaterThan(0, p);
        assertEquals(bookPage, queryResults);
    }

    @Test
    public void whenSearchFilteredBooks_thenReturnPageOfBook() {
        entityManager.persistAndFlush(BookMocks.onTheRoad);
        entityManager.persistAndFlush(BookMocks.infiniteJest);

        Pageable p = PageRequest.of(0, 20);
        Page<Book> bookPage = new PageImpl<>(Lists.newArrayList(BookMocks.onTheRoad), p, 1);

        Page<Book> queryResults = bookRepository.search(BookMocks.onTheRoad.getTitle(),
                BookMocks.onTheRoad.getAuthor(),
                BookMocks.onTheRoad.getCategory(), p);
        assertEquals(bookPage, queryResults);
    }
}
