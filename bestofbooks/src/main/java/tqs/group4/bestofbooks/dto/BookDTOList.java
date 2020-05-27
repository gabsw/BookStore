package tqs.group4.bestofbooks.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BookDTOList implements Serializable{
	
	private List<BookDTO> books;

	public BookDTOList(List<BookDTO> l) {
		super();
		this.books = l;
	}
	
	public BookDTOList() {
		this.books = new ArrayList<>();
	}
	
	public void addBookDTO(BookDTO dto) {
		this.books.add(dto);
	}

	public List<BookDTO> getL() {
		return books;
	}

	public void setL(List<BookDTO> l) {
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
		BookDTOList other = (BookDTOList) obj;
		if (books == null) {
			if (other.books != null)
				return false;
		} else if (!books.equals(other.books))
			return false;
		return true;
	}
	
	

}
