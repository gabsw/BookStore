package tqs.group4.bestofbooks.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tqs.group4.bestofbooks.model.Commission;

@Repository
public interface CommissionRepository extends JpaRepository<Commission, Integer> {
    Page<Commission> findAll(Pageable pageable);

    @Query(value = "SELECT SUM(amount) FROM commissions", nativeQuery = true)
    Double totalAmount();
}
