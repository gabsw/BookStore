package tqs.group4.bestofbooks.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Commissions")
public class Commission {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "amount", nullable = false)
    private double amount;
    @Column(name = "order_id", nullable = false)
    private Integer orderId;

    public Commission() {
    }

    public Commission(Integer id, double amount, Integer orderId) {
        this.id = id;
        this.amount = amount;
        this.orderId = orderId;
    }

    public Integer getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public Integer getOrderId() {
        return orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commission that = (Commission) o;
        return Double.compare(that.amount, amount) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, orderId);
    }
}
