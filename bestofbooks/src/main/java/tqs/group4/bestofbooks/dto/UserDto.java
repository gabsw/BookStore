package tqs.group4.bestofbooks.dto;

import java.util.HashMap;
import java.util.Map;

public class UserDto {

	private String username;
	
	private String userType;
	
	private Map<String, String> attributes;

	public UserDto(String username, String userType) {
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
	
}
