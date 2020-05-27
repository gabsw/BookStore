package tqs.group4.bestofbooks.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import tqs.group4.bestofbooks.model.Book;

public class BookListDTO implements Serializable{
	
	private List<Book> books;

	public BookListDTO(List<Book> l) {
		super();
		this.books = l;
	}
	
	public BookListDTO() {
		this.books = new ArrayList<>();
	}
	
	public void addBook(Book dto) {
		this.books.add(dto);
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> l) {
		this.books = l;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((books == null) ? 0 : books.hashCode());
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
		BookListDTO other = (BookListDTO) obj;
		if (books == null) {
			if (other.books != null)
				return false;
		}
		else if (!books.equals(other.books))
			return false;
		return true;
	}
	
	

}
