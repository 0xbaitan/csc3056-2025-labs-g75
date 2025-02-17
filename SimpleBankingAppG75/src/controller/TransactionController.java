package controller;

import java.util.Calendar;
import java.util.Vector;


import model.Transaction;

public class TransactionController {
    
    private static volatile TransactionController instance;
    private final Vector<Transaction> transactions;
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
  
	public void addTransaction(String account_number, double amount) { 
		Transaction aTransaction =  new Transaction(account_number, amount, Calendar.getInstance().getTime());
		transactions.add(aTransaction);
	}
    
}
