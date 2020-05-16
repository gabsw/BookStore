package tqs.group4.bestofbooks.model;


import javax.persistence.*;

@Entity
@Table(name = "Revenues")
public class Revenue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "sales_amount", nullable = false)
    private double amount;
    @Column(name = "publisher_name", nullable = false)
    private String publisherName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_order_id", referencedColumnName = "id", nullable = false)
    private BooksOrders booksOrders;

    public Revenue() {
    }

    public Revenue(double amount, BooksOrders bookOrder, String publisherName) {
        this.amount = amount;
        this.booksOrders = bookOrder;
        this.publisherName = publisherName;
    }

    public Integer getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public BooksOrders getBookOrder() {
        return booksOrders;
    }

    public String getPublisherName() {
        return publisherName;
    }
}
