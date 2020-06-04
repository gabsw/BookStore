package tqs.group4.bestofbooks.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import tqs.group4.bestofbooks.model.Book;

public class BookDTO implements Serializable{
	
	@NotBlank(message = "Isbn cannot be null or whitespace")
	@Pattern(regexp="[0-9]{13}")
	private String isbn;
	@NotBlank(message = "Title cannot be null or whitespace")
    private String title;
	@NotBlank(message = "Author cannot be null or whitespace")
    private String author;
	@NotBlank(message = "Description cannot be null or whitespace")
    private String description;
	@NotNull(message = "Price cannot be null")
    private double price;
	@NotNull(message = "Quantity cannot be null")
	@Min(value = 1, message = "Quantity cannot not be less than 1")
    private int quantity;
	@NotBlank(message = "Category cannot be null or whitespace")
    private String category;
	
	
	public BookDTO(@NotBlank(message = "Isbn cannot be null or whitespace") @Pattern(regexp = "[0-9]{13}") String isbn,
			@NotBlank(message = "Title cannot be null or whitespace") String title,
			@NotBlank(message = "Author cannot be null or whitespace") String author,
			@NotBlank(message = "Description cannot be null or whitespace") String description,
			@NotNull(message = "Price cannot be null") double price,
			@NotNull(message = "Quantity cannot be null") @Min(value = 1, message = "Quantity cannot not be less than 1") int quantity,
			@NotBlank(message = "Category cannot be null or whitespace") String category) {
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.category = category;
	}


	public String getIsbn() {
		return isbn;
	}


	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}

	public Book getBookObject(String publisherName) {
		return new Book(this.getIsbn(),
				this.getTitle(),
				this.getAuthor(),
				this.getDescription(),
				this.getPrice(),
				this.getQuantity(),
				this.getCategory(),
				publisherName);
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BookDTO bookDTO = (BookDTO) o;
		return Objects.equals(isbn, bookDTO.isbn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(isbn);
	}
}
