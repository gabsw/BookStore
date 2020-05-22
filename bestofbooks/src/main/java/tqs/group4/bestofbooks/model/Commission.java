package tqs.group4.bestofbooks.model;

import javax.persistence.*;

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

    public Commission(double amount, Integer orderId) {
        this.amount = amount;
        this.orderId = orderId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public Integer getOrderId() {
        return orderId;
    }
}
