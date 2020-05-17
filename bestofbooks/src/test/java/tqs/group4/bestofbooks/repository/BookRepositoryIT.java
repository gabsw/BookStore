package tqs.group4.bestofbooks.repository;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import tqs.group4.bestofbooks.mocks.BookMocks;
import tqs.group4.bestofbooks.model.Book;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class BookRepositoryIT {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void before() {
        entityManager.createNativeQuery("TRUNCATE books, orders, commissions, books_orders, revenues").executeUpdate();
    }

    @AfterEach
    public void after() {
        entityManager.remove(BookMocks.infiniteJest);
        entityManager.remove(BookMocks.onTheRoad);
        entityManager.flush();
    }

    @Test
    public void whenFindByExistentIsbn_thenReturnBook() {
        entityManager.persist(BookMocks.onTheRoad);
        entityManager.flush();

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
        entityManager.persist(BookMocks.onTheRoad);
        entityManager.persist(BookMocks.infiniteJest);
        entityManager.flush();

        Pageable p = PageRequest.of(0, 20);
        Page<Book> bookPage = new PageImpl<>(Lists.newArrayList(BookMocks.infiniteJest, BookMocks.onTheRoad), p, 2);

        Page<Book> queryResults = bookRepository.findByQuantityGreaterThan(0, p);
        assertAll("findByQuantityDB",
                () -> assertTrue(bookPage.getContent().contains(BookMocks.onTheRoad)),
                () -> assertTrue(bookPage.getContent().contains(BookMocks.infiniteJest))
        );
    }

    @Test
    public void whenSearchFilteredBooks_thenReturnPageOfBook() {
        entityManager.persist(BookMocks.onTheRoad);
        entityManager.persist(BookMocks.infiniteJest);
        entityManager.flush();

        Pageable p = PageRequest.of(0, 20);
        Page<Book> bookPage = new PageImpl<>(Lists.newArrayList(BookMocks.onTheRoad), p, 1);

        Page<Book> queryResults = bookRepository.search(BookMocks.onTheRoad.getTitle(),
                BookMocks.onTheRoad.getAuthor(),
                BookMocks.onTheRoad.getCategory(), p);
        assertEquals(bookPage, queryResults);
    }
}
