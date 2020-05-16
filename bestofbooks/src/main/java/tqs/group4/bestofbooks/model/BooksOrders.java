package tqs.group4.bestofbooks.model;


import javax.persistence.*;

@Entity
@Table(name = "books_orders")
public class BooksOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "isbn", referencedColumnName = "isbn", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private Order order;

    public BooksOrders() {
    }

    public Integer getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public Order getOrder() {
        return order;
    }
}
