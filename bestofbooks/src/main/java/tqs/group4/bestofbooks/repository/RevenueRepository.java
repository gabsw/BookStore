package tqs.group4.bestofbooks.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tqs.group4.bestofbooks.model.Revenue;


public interface RevenueRepository extends JpaRepository<Revenue, Integer> {
    Page<Revenue> findByPublisherName(String publisherName, Pageable pageable);

    // TODO: Needs Book_orders tables to confirm query
    @Query(value = "" +
            "SELECT SUM(r.sales_amount) " +
            "FROM revenues r " +
            "join books_orders bo on r.book_order_id = bo.id " +
            "join books b on bo.isbn = b.isbn " +
            "where b.publisher_name = :publisherName", nativeQuery = true)
    Double totalSalesAmountByPublisher(String publisherName);
}
