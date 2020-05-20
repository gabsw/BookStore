package tqs.group4.bestofbooks.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IncomingBookOrderDTO that = (IncomingBookOrderDTO) o;
        return quantity == that.quantity &&
                Objects.equals(isbn, that.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, quantity);
    }
}
