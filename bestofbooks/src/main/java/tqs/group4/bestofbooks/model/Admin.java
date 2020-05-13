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
	private String passwordHash;
	
	public Admin(String username, String passwordHash) {
		this.username = username;
		this.passwordHash = passwordHash;
	}

	public Admin() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

}
