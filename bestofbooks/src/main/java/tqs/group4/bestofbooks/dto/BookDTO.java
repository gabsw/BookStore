package tqs.group4.bestofbooks.dto;

import java.io.Serializable;

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + quantity;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		BookDTO other = (BookDTO) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		}
		else if (!author.equals(other.author))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		}
		else if (!category.equals(other.category))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		}
		else if (!description.equals(other.description))
			return false;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		}
		else if (!isbn.equals(other.isbn))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (quantity != other.quantity)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		}
		else if (!title.equals(other.title))
			return false;
		return true;
	}

}
