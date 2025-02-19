package controller;

import java.util.Vector;

import model.Account;
import model.User;

public class UserController {
	
	private static volatile UserController instance;
	
	private final Vector<User> users;

	private static final AccountController accountController = AccountController.getInstance();
	
	private UserController() {
		users = new Vector<User>();
		loadUserData();
	}
	
	public static UserController getInstance() {
		UserController local = UserController.instance;
		if(local == null) {
			synchronized (UserController.class) {
				if(local == null) {
				UserController.instance = local = new UserController();
				}
			}
		}
		
		return local;
	}

	private void loadUserData() {
		// structure of each record: username (email address), password, first_name, last_name, mobile_number
		
		// in the ideal case (real deployment of the app), we will read from file or database, but let's hard-code for now
		User aUser = new User("mike", "my_passwd", "Mike", "Smith", "07771234567");
		users.add(aUser);
		
		aUser = new User("james.cameron@gmail.com", "angel", "James", "Cameron",  "07777654321");
		users.add(aUser);
		
		aUser = new User("julia.roberts@gmail.com", "change_me",   "Julia", "roberts",   "07770123456");
		users.add(aUser); 
	}
	
	public Vector<User> getUsers() {
		return new Vector<>(users);
	}

	public void printAllUsers() {
		System.out.println("There are: " + users.size() + " users in the system.");	
		System.out.println(String.format("%-25s| %-15s| %-15s| %-15s| %-15s", 
				"username", "password", "first_name", "last_name", "mobile_number"));
		System.out.println("-------------------------------------------------------------------------------------------");
		for  (int i = 0; i < users.size(); i++) {
	        System.out.println(users.get(i).toString());	
		}
		System.out.println();
	}





	public double getAggregateBalance(String username) {
		double aggregateBalance = 0.0;
		Vector<Account> accounts = accountController.getAccounts(username);
		if(accounts.isEmpty()) {
			throw new IllegalArgumentException("No account found for the user: " + username);
		}
		for (Account account : accounts) {
			aggregateBalance += accountController.getBalance(account.getAccountNumber());
		}
		return aggregateBalance;
	}

}
