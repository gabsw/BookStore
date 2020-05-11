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
	private String password_hash;
	
	@Column(name= "tin", nullable = false)
	private String tin;

	public Publisher() {
	}

	public Publisher(String username, String password_hash, String name, String tin) {
		this.username = username;
		this.password_hash = password_hash;
		this.name = name;
		this.tin = tin;
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
