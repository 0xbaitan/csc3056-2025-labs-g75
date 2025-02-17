package controller;

import app.SimpleBankingApp;
import java.text.ParseException;
import model.Account;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class AccountController {
	
	public static Vector<Account> loadAccountData()  {
		// structure of each record: 
		// account number, username (email) of account holder, account type (Standard or Saving), account_opening_date

		Vector<Account> accounts  = new Vector<Account>();
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
		
		return accounts;
	}

}
