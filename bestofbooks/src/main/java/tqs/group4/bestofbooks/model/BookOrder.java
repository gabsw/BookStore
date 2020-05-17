package tqs.group4.bestofbooks.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import tqs.group4.bestofbooks.exception.InvalidIsbnException;
import tqs.group4.bestofbooks.exception.NullBookException;

@Entity
@Table(name = "books_orders")
@Data
@NoArgsConstructor
public class BookOrder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "isbn", referencedColumnName = "isbn", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Order order;

    @Column(name="quantity", nullable=false)
    private int quantity;

    public BookOrder(Book book, Order order, int quantity){
        this.book = book;
        try {
            if (book.getIsbn().length() != 13 || !StringUtils.isNumeric(book.getIsbn()))
                throw new InvalidIsbnException("Isbn length must be 13 numeric digits.");
        } catch (NullPointerException e){
            throw new NullBookException("Book object cannot be null.");
        }
        this.order = order;
        this.quantity = quantity;
    }

    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof BookOrder)) return false;
        BookOrder bookOrder = (BookOrder) o;
        return Objects.equals(book, bookOrder.book)
            && Objects.equals(quantity, bookOrder.quantity);
    }

    public int hashCode(){
        return Objects.hash(book, quantity);
    }
}