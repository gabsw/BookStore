package tqs.group4.bestofbooks.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tqs.group4.bestofbooks.model.Book;

public interface BookRepository extends JpaRepository<Book, String> {
    Book findByIsbn(String isbn);
    Page<Book> findByQuantityGreaterThan(int quantity, Pageable pageable);

    @Query(value = "" +
            "select * from books " +
            "where title = coalesce(cast(:title as varchar), title) and " +
            "author = coalesce(cast(:author as varchar), author) and " +
            "category = coalesce(cast(:category as varchar), category)" +
            "--#pageable\n", nativeQuery = true)
    Page<Book> search(String title, String author, String category, Pageable pageable);
    
    Page<Book> findByPublisherName(String name, Pageable pageable);
}
