
//A User object that has a username and a password
//Used to fetch data from databases and map data
public class User {

	private String username;
	private String password;

	// Constructs a User with a username and a password
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	// Getter for User's username
	public String getUsername() {
		return this.username;
	}

	// Getter for User's password
	public String getPassword() {
		return this.password;
	}

}
