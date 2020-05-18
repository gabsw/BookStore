package tqs.group4.bestofbooks.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "username_buyer", referencedColumnName = "username", nullable = false)
    private Buyer buyer;
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<BookOrder> bookOrders;
//    @OneToMany(mappedBy = "ordersByOrderId", fetch = FetchType.LAZY)
//    private Collection<Commission> commissionsById;

    public Order() {
    }

    public Order(String paymentReference, String address, Double finalPrice, Buyer buyer) {
        this.paymentReference = paymentReference;
        this.address = address;
        this.finalPrice = finalPrice;
        this.buyer = buyer;
        this.bookOrders = new ArrayList<>();
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Buyer getBuyer() {
        return buyer;
    }

    public Collection<BookOrder> getBookOrders() {
        return bookOrders;
    }

    public void addBookOrder(BookOrder bookOrder) {
        this.bookOrders.add(bookOrder);
    }

//    public Collection<Commission> getCommissionsById() {
//        return commissionsById;
//    }
}
