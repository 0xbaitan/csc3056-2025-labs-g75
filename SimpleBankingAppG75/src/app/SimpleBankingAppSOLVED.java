package app;


import controller.AccountController;
import controller.TransactionController;
import controller.UserController;

import java.text.SimpleDateFormat;
import java.util.Vector;
import model.Transaction;

public class SimpleBankingAppSOLVED {

	public static void printAllTransactionsForAccount(String account_number) {
		AccountController accountController = AccountController.getInstance();
		Vector<Transaction> transactions = accountController.getTransactionsForAccount(account_number);
		System.out.println(String.format("%-10s| %-15s| %-15s| %-10s",
				"#", "Account Number", "Amount", "Date"
		));
		System.out.println("--------------------------------------------------------------------------------");
		for(int i = 0; i < transactions.size(); i++) {
			System.out.println(String.format("%-10s| %-15s| %-15s| %-10s",
					i+1, transactions.get(i).getAccountNumber(), "$"+transactions.get(i).getTransactionAmount(), 
					new SimpleDateFormat("(MMM dd, yyyy)").format(transactions.get(i).getTransactionDate())
			));
		}
	}
	public static void main(String[] args) {
		
		UserController userController = UserController.getInstance();
		AccountController accountController = AccountController.getInstance();
		TransactionController transactionController = TransactionController.getInstance();
		
		// let's print them all to see if they have been loaded (populated) properly
		userController.printAllUsers();
		
		System.out.println("Accounts: initial state, after loading...");
		accountController.printAllAccounts();
		
		// let's do some activities on the populated accounts, add transactions, etc.
		// Deposit: adding a transaction with a positive value
		// Withdraw: adding a transaction with a negative value
		transactionController.withdrawAmount("5495-1234", 50.21);
		System.out.println("Account: after the 1st addTransaction function call...");
		accountController.printAllAccounts();
		
		// and some more activities on the accounts
		transactionController.depositAmount("5495-1234", 520.00);
		transactionController.depositAmount("9999-1111", 21.00); // it seems this account does not exist in the loaded (populated) data, 
											// but the addTransaction does not do that check, need to improve that function in future
		// let's print the accounts and their balance to see if the above transaction have impacted their balances
		System.out.println("Account: after the 2nd/3rd addTransaction function calls...");
		accountController.printAllAccounts();

		System.out.println("Transactions for account 5495-1234");
		System.out.println("Printing transactions...");
		printAllTransactionsForAccount("5495-1234");

		

	}

}
