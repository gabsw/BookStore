package tqs.group4.bestofbooks.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class StockDto implements Serializable{
	
	 @NotBlank(message = "Isbn cannot be null or whitespace")
	 @Pattern(regexp="[0-9]{13}")
	 private String isbn;

	 @NotNull(message = "Quantity cannot be null")
	 @Min(value = 1, message = "Quantity cannot not be less than 1")
	 private int quantity;

	public StockDto(@NotBlank(message = "Isbn cannot be null or whitespace") @Pattern(regexp = "[0-9]{13}") String isbn,
			@NotNull(message = "Quantity cannot be null") @Min(value = 1, message = "Quantity cannot not be less than 1") int quantity) {
		this.isbn = isbn;
		this.quantity = quantity;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		result = prime * result + quantity;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StockDto other = (StockDto) obj;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		}
		else if (!isbn.equals(other.isbn))
			return false;
		return (quantity == other.quantity);
	}
	

}
