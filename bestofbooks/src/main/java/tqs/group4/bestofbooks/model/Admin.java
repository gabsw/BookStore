package tqs.group4.bestofbooks.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Admin")
public class Admin {
	
	@Id
    @Column(name = "username", nullable = false)
    private String username;
	
	@Column(name= "password_hash", nullable = false)
	private String password_hash;
	
	public Admin(String username, String password_hash) {
		this.username = username;
		this.password_hash = password_hash;
	}

	public Admin() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword_hash() {
		return password_hash;
	}

	public void setPassword_hash(String password_hash) {
		this.password_hash = password_hash;
	}

}
