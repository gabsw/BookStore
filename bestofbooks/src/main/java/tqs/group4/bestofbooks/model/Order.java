package tqs.group4.bestofbooks.model;


import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "payment_reference", nullable = false, length = 20)
    private String paymentReference;
    @Basic
    @Column(name = "address", nullable = false, length = 100)
    private String address;
    @Basic
    @Column(name = "final_price", nullable = false, precision = 2)
    private Double finalPrice;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username_buyer", referencedColumnName = "username", nullable = false)
    private Buyer buyersByUsernameBuyer;
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private Collection<BooksOrders> booksOrdersById;
//    @OneToMany(mappedBy = "ordersByOrderId", fetch = FetchType.LAZY)
//    private Collection<Commission> commissionsById;

    public Order() {
    }

    public Order(String paymentReference, String address, Double finalPrice, Buyer buyersByUsernameBuyer) {
        this.paymentReference = paymentReference;
        this.address = address;
        this.finalPrice = finalPrice;
        this.buyersByUsernameBuyer = buyersByUsernameBuyer;
    }

    public Integer getId() {
        return id;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public String getAddress() {
        return address;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public Buyer getBuyersByUsernameBuyer() {
        return buyersByUsernameBuyer;
    }

    public Collection<BooksOrders> getBooksOrdersById() {
        return booksOrdersById;
    }

//    public Collection<Commission> getCommissionsById() {
//        return commissionsById;
//    }
}
