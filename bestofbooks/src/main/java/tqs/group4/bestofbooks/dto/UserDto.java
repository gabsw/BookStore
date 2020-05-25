package tqs.group4.bestofbooks.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotBlank;

public class UserDto implements Serializable{

	@NotBlank(message = "Username cannot be null or whitespace")
	private String username;
	
	@NotBlank(message = "User type cannot be null or whitespace")
	private String userType;
	
	private Map<String, String> attributes;

	public UserDto(@NotBlank(message = "User type cannot be null or whitespace") String username,@NotBlank(message = "User type cannot be null or whitespace") String userType) {
		this.username = username;
		this.userType = userType;
		this.attributes = new HashMap<>();
	}
	
	public void addAttribute(String key, String value) {
		this.attributes.put(key, value);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result + ((userType == null) ? 0 : userType.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		UserDto other = (UserDto) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		}
		else if (!attributes.equals(other.attributes))
			return false;
		if (userType == null) {
			if (other.userType != null)
				return false;
		}
		else if (!userType.equals(other.userType))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		}
		else if (!username.equals(other.username)) {
			return false;
		}
		return true;
	}
	
	
	
}
