package tqs.group4.bestofbooks.dto;

public class IncomingBookOrderDTO {
    private String isbn;
    private int quantity;

    public IncomingBookOrderDTO(String isbn, int quantity) {
        this.isbn = isbn;
        this.quantity = quantity;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getQuantity() {
        return quantity;
    }
}
