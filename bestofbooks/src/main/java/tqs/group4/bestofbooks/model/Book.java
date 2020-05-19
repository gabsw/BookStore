package tqs.group4.bestofbooks.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Books")
public class Book {
    @Id
    @NaturalId
    @Column(name = "isbn", nullable = false)
    private String isbn;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "author", nullable = false)
    private String author;
    @Column(name = "description")
    private String description;
    @Column(name = "price", nullable = false, precision = 2)
    private double price;
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Column(name = "category", nullable = false)
    private String category;
    @Column(name = "publisher_name", nullable = false)
    private String publisherName;
    @JsonIgnore
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<BookOrder> bookOrders;

    public Book() {
    }

    public Book(String isbn, String title, String author, String description, double price, int quantity,
                String category, String publisherName) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.publisherName = publisherName;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
		return description;
	}

	public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public String getPublisherName() {
        return publisherName;
    }


    public Collection<BookOrder> getBookOrders() {
        return bookOrders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}
