package tqs.group4.bestofbooks.dto;

import tqs.group4.bestofbooks.model.BookOrder;

public class BookOrderDTO {
    private String isbn;
    private String title;
    private String author;
    private Integer quantity;

    BookOrderDTO(String isbn, String title, String author, Integer quantity) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
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

    public Integer getQuantity() {
        return quantity;
    }

    static BookOrderDTO fromBookOrder(BookOrder bookOrder) {
        return new BookOrderDTO(
                bookOrder.getBook().getIsbn(),
                bookOrder.getBook().getTitle(),
                bookOrder.getBook().getAuthor(),
                bookOrder.getQuantity());
    }
}
