package tqs.group4.bestofbooks.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Buyers")
public class Buyer {
	
	@Id
    @Column(name = "username", nullable = false)
    private String username;
	
	@Column(name= "password_hash", nullable = false)
	private String passwordHash;

	
	public Buyer(String username, String passwordHash) {
		this.username = username;
		this.passwordHash = passwordHash;
	}

	public Buyer() {
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
