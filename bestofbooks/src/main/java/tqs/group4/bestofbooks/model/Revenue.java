package tqs.group4.bestofbooks.model;


import javax.persistence.*;

@Entity
@Table(name = "Revenues")
public class Revenue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "sales_amount", nullable = false, precision = 2)
    private double amount;
    @Column(name = "publisher_name", nullable = false)
    private String publisherName;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "book_order_id", referencedColumnName = "id", nullable = false)
    private BookOrder bookOrder;

    public Revenue() {
    }

    public Revenue(double amount, BookOrder bookOrder, String publisherName) {
        this.amount = amount;
        this.bookOrder = bookOrder;
        this.publisherName = publisherName;
    }

    public Integer getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public BookOrder getBookOrder() {
        return bookOrder;
    }

    public String getPublisherName() {
        return publisherName;
    }
}
