package tqs.group4.bestofbooks.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Publishers")
public class Publisher {
	
	@Id
	@Column(name= "name", nullable = false)
	private String name;
	
    @Column(name = "username", nullable = false)
    private String username;
	
	@Column(name= "password_hash", nullable = false)
	private String passwordHash;
	
	@Column(name= "tin", nullable = false)
	private String tin;

	public Publisher() {
	}

	public Publisher(String username, String passwordHash, String name, String tin) {
		this.username = username;
		this.passwordHash = passwordHash;
		this.name = name;
		this.tin = tin;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

}
