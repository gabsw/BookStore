package tqs.group4.bestofbooks.model;

import javax.persistence.*;

@Entity
@Table(name = "books_orders")
public class BookOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "isbn", referencedColumnName = "isbn", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private Order order;

    private Integer quantity;

    public BookOrder() {
    }

    public BookOrder(Book book, Order order, Integer quantity) {
        this.book = book;
        this.order = order;
        this.quantity = quantity;
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

    public Integer getQuantity() {
        return quantity;
    }
}
