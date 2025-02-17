package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;

import model.Account;
import model.Transaction;

public class AccountController {
	
	private static volatile AccountController instance;

	private final Vector<Account> accounts;
	
	private static final TransactionController transactionController = TransactionController.getInstance();

	private AccountController() {
		accounts = new Vector<Account>();
		loadAccountData();
	}

	public static AccountController getInstance() {
			AccountController local = AccountController.instance;
		if (local == null) {
			synchronized (AccountController.class) {
                    local = AccountController.instance;
				if (local == null) {
					AccountController.instance = local = new AccountController();
				}
			}
		}
		return local;
		
	}

	
	private void loadAccountData()  {
		// structure of each record: 
		// account number, username (email) of account holder, account type (Standard or Saving), account_opening_date

	
		// in the ideal case, we will read from file or database, but let's hard-code for now
		Account anAccount;
		try {
			anAccount = new Account("5495-1234", "mike", "Standard", new SimpleDateFormat("dd/MM/yyyy").parse("20/08/2019"));
			accounts.add(anAccount);
			
			anAccount = new Account("5495-1239", "mike", "Standard", new SimpleDateFormat("dd/MM/yyyy").parse("20/08/2020"));
			accounts.add(anAccount);

			anAccount = new Account("5495-1291", "mike", "Saving", new SimpleDateFormat("dd/MM/yyyy").parse("21/07/2019"));
			accounts.add(anAccount);

			anAccount = new Account("5495-6789", "David.McDonald@gmail.com", "Saving", new SimpleDateFormat("dd/MM/yyyy").parse("20/08/2019"));
			accounts.add(anAccount);
			

		} catch (ParseException e) {			
			e.printStackTrace();
		}  
		
		
	}

	public void printAllAccounts() {
		System.out.println("There are: " + accounts.size() + " accounts in the system.");
		//System.out.println("Account_number | username_of_account_holder | account_type | account_opening_date");
	
		System.out.println(String.format("%-10s| %-30s| %-10s| %-15s| %-15s", 
				"Account #", "username_of_account_holder", "type", "opening_date", "Balance"));
		System.out.println("--------------------------------------------------------------------------------");
		
		for  (int i = 0; i < accounts.size(); i++) 
	        System.out.println(accounts.get(i).toString() + "| $" + getBalance(accounts.get(i).getAccountNumber()));
		
		System.out.println();
	}

	/**
	 * Calculate the balance of a given account (by its number). To do that, it needs to go over all transactions
	 * that match the account and get their sum total. For example, if an account has only two transactions in the 
	 * system, with values = $10.79 and $-140, the balance would be $-129.21
	 * 
	 * @param account_number
	 * @return A double value, being the balance of the account
	 */
	public double getBalance(String account_number) {
		double balance = 0.0;
		Vector<Transaction> transactions = transactionController.getTransactions();
		for (int i = 0; i < transactions.size(); i++) {
			if (transactions.get(i).getAccountNumber().equals(account_number)) {
				balance += transactions.get(i).getTransactionAmount();
			}
		}
		return balance;
		
	}

}
