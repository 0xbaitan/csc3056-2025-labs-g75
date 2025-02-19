package controller;

import java.util.Calendar;
import java.util.Optional;
import java.util.Vector;


import model.Transaction;

public class TransactionController {
    
    private static volatile TransactionController instance;
    private final Vector<Transaction> transactions;
	private static final AccountController accountController = AccountController.getInstance();
    private TransactionController() {
        transactions = new Vector<Transaction>();
    }
    
    public static TransactionController getInstance() {
    	TransactionController local = TransactionController.instance;
    	
    	if(local == null) {
    		synchronized (TransactionController.class) {
    			local = TransactionController.instance;
    			if(local == null) {
    				TransactionController.instance = local = new TransactionController();
    			}
    		}
    	}
    	return local;
	}
	
	public Vector<Transaction> getTransactions() {
		return new Vector<>(transactions);
	}


	private void validateAmount(double amount) {
		if(amount <= 0) {
			throw new IllegalArgumentException("Amount must be greater than $0.00");
		}
	}

	private void validateAccountNumber(String account_number, Optional<String> message) {
		if(!accountController.accountExists(account_number)) {
			throw new IllegalArgumentException(message.orElse("Account number does not exist"));
		}

	}

	private void validateSufficientBalance(String account_number, double amount) {
		validateAccountNumber(account_number, Optional.empty());
		if(accountController.getBalance(account_number) < amount) {
			throw new IllegalArgumentException("Insufficient balance in account: " + account_number);
		}
	}




	public void depositAmount(String account_number, double amount) {
		validateAmount(amount);
		validateAccountNumber(account_number, Optional.empty());
		addTransaction(account_number, amount);
	}

	public void withdrawAmount(String account_number, double amount) {
		validateAmount(amount);
		validateAccountNumber(account_number, Optional.empty());
		addTransaction(account_number, -amount);
	}


	public void transferAmount(String fromAccountNumber, String toAccountNumber, double amount) {
		validateAmount(amount);
		validateAccountNumber(fromAccountNumber, Optional.of("From account number does not exist"));
		validateSufficientBalance(fromAccountNumber, amount);
		validateAccountNumber(toAccountNumber, Optional.of("To account number does not exist"));
		withdrawAmount(fromAccountNumber, amount);
		depositAmount(toAccountNumber, amount);
	}
  
	private void addTransaction(String account_number, double amount) { 
		Transaction aTransaction =  new Transaction(account_number, amount, Calendar.getInstance().getTime());
		transactions.add(aTransaction);
	}

    
}
