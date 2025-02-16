package tests;

import utils.TestUtils;

import java.util.Date;

import model.Transaction;

public class TransactionTest {
	
public static void testAccountConstructor() {
		
		String test_account_number = "303435";
		double test_transaction_amount = 40.00;
		Date test_transaction_date = new Date(2025, 02, 15);
		
				
		Transaction testTransaction= new Transaction(test_account_number, test_transaction_amount, test_transaction_date);
		
		System.out.println("Starting method");
		
		final String TEST_CASE_ACCOUNT_NUMBER = "TC1-getAccountNumber";
		final String TEST_CASE_TRANSACTION_AMOUNT = "TC2-getTransactionAmount";
		final String TEST_CASE_TRANSACTION_DATE = "TC3-getTransactionDate";
		
		
	 if (testTransaction.getAccountNumber() ==  test_account_number) {
         TestUtils.printTestPassed(TEST_CASE_ACCOUNT_NUMBER);
     } else {
         TestUtils.printTestFailed(TEST_CASE_ACCOUNT_NUMBER);
     }
	
     if (testTransaction.getTransactionAmount() == test_transaction_amount) {
         TestUtils.printTestPassed(TEST_CASE_TRANSACTION_AMOUNT);
     } else {
         TestUtils.printTestFailed(TEST_CASE_TRANSACTION_AMOUNT);
     }

     if (testTransaction.getTransactionDate() == test_transaction_date) {
         TestUtils.printTestPassed(TEST_CASE_TRANSACTION_DATE);
     } else {
         TestUtils.printTestFailed(TEST_CASE_TRANSACTION_DATE);
     }

} 
public static void main(String args[]) {
	testAccountConstructor();
}
}
