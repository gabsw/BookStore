package tqs.group4.bestofbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tqs.group4.bestofbooks.model.Commission;

@Repository
public interface CommissionRepository extends JpaRepository<Commission, Integer> {
    @Query(value = "SELECT SUM(amount) FROM commissions", nativeQuery = true)
    double totalAmount();
}
