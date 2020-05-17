package tqs.group4.bestofbooks.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode()
@Entity
@Data
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Exclude
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "payment_reference", nullable = false)
    private String paymentReference;
    @Column(name = "username_buyer", nullable = false)
    private String buyerUsername;

    @OneToMany(
        mappedBy = "order",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<BookOrder> bookOrders;

    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "final_price", nullable = false)
    private double finalPrice;

    public Order(String paymentReference, String usernameBuyer, 
        List<BookOrder> bookOrders, String address, double finalPrice){
        this.paymentReference = paymentReference;
        this.buyerUsername = usernameBuyer;
        this.bookOrders = bookOrders;
        this.address = address;
        this.finalPrice = finalPrice;
    }

    public void addBookOrder(BookOrder bookOrder){
        bookOrders.add(bookOrder);
        bookOrder.setOrder(this);
    }

    public void removeBookOrder(BookOrder bookOrder){
        bookOrders.remove(bookOrder);
        bookOrder.setOrder(null);
    }
}