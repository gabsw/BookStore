package tqs.group4.bestofbooks.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tqs.group4.bestofbooks.model.Revenue;


public interface RevenueRepository extends JpaRepository<Revenue, Integer> {
    Page<Revenue> findByPublisherName(String publisherName, Pageable pageable);

    @Query(value = "" +
            "SELECT SUM(sales_amount) " +
            "FROM revenues " +
            "where publisher_name = :publisherName", nativeQuery = true)
    Double totalSalesAmountByPublisher(String publisherName);
}
