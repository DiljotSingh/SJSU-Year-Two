import java.util.*;

//Can authenticate Users' sign up / sign in requests and access Users' credentials
public class UserManager {
	
	private static HashMap<String,String> userDatabase; //contains all users in the system
	
	//Constructs a userDatabase
	public UserManager()
	{
		userDatabase = new HashMap<String,String>();
	}
	
	//Attempts to add a User to the System
	public static boolean addUser(User u)
	{
		if (userDatabase.containsKey(u.getUsername()))
		{
			return false; //This user name has already been taken
		}
		else
		{
			userDatabase.put(u.getUsername(), u.getPassword()); //Adds user to the User database
			ReservationManager.addUser(u.getUsername()); //Creates an empty ArrayList of Reservations for this User
			return true; //User's credentials were successfully added
		}
	}
	
	//Authenticates a User's sign in request
	public static boolean authenticateUser(User u)
	{
		if (!userDatabase.containsKey(u.getUsername()))
		{
			return false; //User's entered username was not found
		
		}
		else if (!(userDatabase.get(u.getUsername()).equals(u.getPassword()))) //combination not found (i.e bad password)
		{
			return false; //User's entered combination was not found
		}
		return true; //User's entered credentials were valid
	}
	
	// Gets all credentials of all Users (used to write to reservations.txt in
	// 'Exit' prompt)
	public static HashMap<String,String> getUserDataBase()
	{
		return userDatabase;
	}
	
}
