package controller;

import app.SimpleBankingApp;
import model.User;
import java.util.Vector;

public class UserController {

	public static Vector<User> loadUserData() {
		// structure of each record: username (email address), password, first_name, last_name, mobile_number
		Vector<User> users = new Vector<User>();
		// in the ideal case (real deployment of the app), we will read from file or database, but let's hard-code for now
		User aUser = new User("mike", "my_passwd", "Mike", "Smith", "07771234567");
		users.add(aUser);
		
		aUser = new User("james.cameron@gmail.com", "angel", "James", "Cameron",  "07777654321");
		users.add(aUser);
		
		aUser = new User("julia.roberts@gmail.com", "change_me",   "Julia", "roberts",   "07770123456");
		users.add(aUser); 
		
		return users;
		
	}

}
