package tqs.group4.bestofbooks.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class IncomingBookOrderDTO {
    @NotBlank(message = "Isbn cannot be null or whitespace")
    @Pattern(regexp="[0-9]{13}")
    private String isbn;
    @NotNull(message = "Order quantity cannot be null")
    @Min(value = 1, message = "Order quantity should not be less than 1")
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
