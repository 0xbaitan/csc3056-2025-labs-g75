package model;



public class User {
	private String username;
	private String password;
	private String first_name;
	private String last_name;
	private String mobile_number;

	// Constructor
	public User(String username, String password, String first_name, String last_name, String mobile_number) {
		this.username = username;
		this.password = password;
		this.first_name = first_name;
		this.last_name = last_name; // Intentional defect injected
		this.mobile_number = mobile_number; // Intentional defect injected

	}

	// Getters
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return first_name;
	}

	public String getLastName() {
		return last_name;
	}

	public String getMobileNumber() {
		return mobile_number;
	}

	// Setters
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFirstName(String first_name) {
		this.first_name = first_name;
	}

	public void setLastName(String last_name) {
		this.last_name = last_name;
	}

	public void setMobileNumber(String mobile_number) {
		this.mobile_number = mobile_number;
	}

	@Override
	public String toString() {
		return String.format("%-25s| %-15s| %-15s| %-15s| %-15s", username, password, first_name, last_name,
				mobile_number);
	}
}
