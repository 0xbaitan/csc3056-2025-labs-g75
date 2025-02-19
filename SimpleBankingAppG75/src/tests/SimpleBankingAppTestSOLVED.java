package tests;

import controller.AccountController;
import controller.TransactionController;
import controller.UserController;
import java.util.List;
import java.util.Vector;
import model.Account;
import utils.TestUtils;

public class SimpleBankingAppTestSOLVED {

    
	private static final TransactionController transactionController = TransactionController.getInstance();
	private static final AccountController accountController = AccountController.getInstance();
	private static final UserController userController = UserController.getInstance();

	
	
	public static void testDepositsWithInvalidAmount() {
		final String TEST_ACCOUNT_NUMBER = "5495-1234";
		double depositAmount = 0.00;
		
		try {
			transactionController.depositAmount(TEST_ACCOUNT_NUMBER, depositAmount);
			TestUtils.printTestFailed("testDeposits: TC2 failed - $0.00 accepted as a valid deposit amount");
			transactionController.withdrawAmount(TEST_ACCOUNT_NUMBER, depositAmount);
		} catch(IllegalArgumentException e) {
			TestUtils.printTestPassed("testDeposits: TC2 passed - $0.00 not accepted as a valid deposit amount");
		}
		
		 depositAmount = -50.00;
		 
		 try {
				transactionController.depositAmount(TEST_ACCOUNT_NUMBER, depositAmount);
				TestUtils.printTestFailed("testDeposits: TC3 failed - negative values accepted as a valid deposit amount");
				transactionController.withdrawAmount(TEST_ACCOUNT_NUMBER, depositAmount);
			} catch(IllegalArgumentException e) {
				TestUtils.printTestPassed("testDeposits: TC3 passed - negative values not accepted as a valid deposit amount");
			}
	}

	public static void testWithdrawalsWithInvalidAmount() {
		final String TEST_ACCOUNT_NUMBER = "5495-1234";
		double withdrawalAmount = 0.00;

		try {
			transactionController.withdrawAmount(TEST_ACCOUNT_NUMBER, withdrawalAmount);
			TestUtils.printTestFailed("testWithdrawals: TC2 failed - $0.00 accepted as a valid withdrawal amount");
			transactionController.depositAmount(TEST_ACCOUNT_NUMBER, withdrawalAmount);
		} catch(IllegalArgumentException e) {
			TestUtils.printTestPassed("testWithdrawals: TC2 passed - $0.00 not accepted as a valid withdrawal amount");
		}

		 withdrawalAmount = -50.00;

		 try {
				transactionController.withdrawAmount(TEST_ACCOUNT_NUMBER, withdrawalAmount);
				TestUtils.printTestFailed("testWithdrawals: TC3 failed - negative values accepted as a valid withdrawal amount");
				transactionController.depositAmount(TEST_ACCOUNT_NUMBER, withdrawalAmount);
			} catch(IllegalArgumentException e) {
				TestUtils.printTestPassed("testWithdrawals: TC3 passed - negative values not accepted as a valid withdrawal amount");
			}
	}
	
	// this test method (test case) verifies if the Deposit feature works properly
	public static void testDeposits() {
		
		final String TEST_ACCOUNT_NUMBER = "5495-1234";
		
		// 1-Setup phase
		double balanceBefore = accountController.getBalance(TEST_ACCOUNT_NUMBER); 
		double depositAmount = 50.21;
		
		// 2-Exercise phase
		transactionController.depositAmount(TEST_ACCOUNT_NUMBER, depositAmount);
		double balanceAfter = accountController.getBalance(TEST_ACCOUNT_NUMBER);
		
		// 3-verify
		assert balanceBefore + depositAmount == balanceAfter;
		if (balanceBefore + depositAmount == balanceAfter)
			TestUtils.printTestPassed("testDeposits: TC1 passed");
		else {
			TestUtils.printTestFailed(
					String.format("""
			testDeposits: TC1 FAILED XXX: balanceBefore + depositAmount != balanceAfter 
			balanceBefore = %.2f ; depositAmount = %.2f ; balanceAfter = %.2f""", balanceBefore , depositAmount , balanceAfter));

			
		}
		
		// 4-tear-down: put the system state back in where it was
		// read more about the tear-down phase of test cases: http://xunitpatterns.com/Four%20Phase%20Test.html
		transactionController.withdrawAmount(TEST_ACCOUNT_NUMBER, depositAmount);
	}


	public static void testAggregateBalance() {
		final String TEST_USERNAME = "mike";
		final String TEST_ACCOUNT_NUMBER_1 = "5495-1234"; // Mike savings account 1
		final String TEST_ACCOUNT_NUMBER_2 = "5495-1239"; // Mike savings account 2
		final String TEST_ACCOUNT_NUMBER_3 = "5495-1291"; // Mike savings account 3

	    Vector<Account> accounts = accountController.getAccounts(TEST_USERNAME);

		// 1-Pre-checks
		if(accounts.size() != 3) {
			TestUtils.printTestFailed("testAggregateBalance: TC1 failed - Mike does not have 3 accounts");
			return;
		}

	    List<String> accountNumbers = accounts.stream().map(Account::getAccountNumber).toList();

		if(!accountNumbers.contains(TEST_ACCOUNT_NUMBER_1)) {
			TestUtils.printTestFailed("testAggregateBalance: TC1 failed - Mike does not have account 1");
			return;
		}

		if(!accountNumbers.contains(TEST_ACCOUNT_NUMBER_2)) {
			TestUtils.printTestFailed("testAggregateBalance: TC1 failed - Mike does not have account 2");
			return;
		}

		if(!accountNumbers.contains(TEST_ACCOUNT_NUMBER_3)) {
			TestUtils.printTestFailed("testAggregateBalance: TC1 failed - Mike does not have account 3");
			return;
		}


		double balanceBefore = accountController.getBalance(TEST_ACCOUNT_NUMBER_1) + accountController.getBalance(TEST_ACCOUNT_NUMBER_2) + accountController.getBalance(TEST_ACCOUNT_NUMBER_3);

		double firstDepositToAccount1 = 2000.00;
		double secondDepositToAccount2 = 3000.00;
		double thirdDepositToAccount3 = 500.00;
		double firstWithdrawalFromAccount1 = 1000.00;
		double secondWithdrawalFromAccount2 = 500.00;
		double thirdWithdrawalFromAccount3 = 100.00;

		double netDeposit = firstDepositToAccount1 + secondDepositToAccount2 + thirdDepositToAccount3;
		double netWithdrawal = firstWithdrawalFromAccount1 + secondWithdrawalFromAccount2 + thirdWithdrawalFromAccount3;
		double balanceAfter = balanceBefore + netDeposit - netWithdrawal;

		transactionController.depositAmount(TEST_ACCOUNT_NUMBER_1, firstDepositToAccount1);
		transactionController.depositAmount(TEST_ACCOUNT_NUMBER_2, secondDepositToAccount2);
		transactionController.depositAmount(TEST_ACCOUNT_NUMBER_3, thirdDepositToAccount3);

		transactionController.withdrawAmount(TEST_ACCOUNT_NUMBER_1, firstWithdrawalFromAccount1);
		transactionController.withdrawAmount(TEST_ACCOUNT_NUMBER_2, secondWithdrawalFromAccount2);
		transactionController.withdrawAmount(TEST_ACCOUNT_NUMBER_3, thirdWithdrawalFromAccount3);

		double aggregateBalance = userController.getAggregateBalance(TEST_USERNAME);

		assert balanceAfter == aggregateBalance;
		if (balanceAfter == aggregateBalance)
			TestUtils.printTestPassed("testAggregateBalance: TC1 passed");
		else {
			TestUtils.printTestFailed(
					String.format("""
			testAggregateBalance: TC1 FAILED XXX: balanceAfter != aggregateBalance 
			balanceAfter = %.2f ; aggregateBalance = %.2f""", balanceAfter , aggregateBalance));
		}


		// 4-tear-down: put the system state back in where it was
		transactionController.depositAmount(TEST_ACCOUNT_NUMBER_1, firstWithdrawalFromAccount1);
		transactionController.depositAmount(TEST_ACCOUNT_NUMBER_2, secondWithdrawalFromAccount2);
		transactionController.depositAmount(TEST_ACCOUNT_NUMBER_3, thirdWithdrawalFromAccount3);

		transactionController.withdrawAmount(TEST_ACCOUNT_NUMBER_1, firstDepositToAccount1);
		transactionController.withdrawAmount(TEST_ACCOUNT_NUMBER_2, secondDepositToAccount2);
		transactionController.withdrawAmount(TEST_ACCOUNT_NUMBER_3, thirdDepositToAccount3);
 
	}

	// this test method (test case) verifies if the Withdraw feature works properly
	
	public static void testWithdrawals() {
		final String TEST_ACCOUNT_NUMBER = "5495-1234";
		
		// 1-Setup phase
		double balanceBefore = accountController.getBalance(TEST_ACCOUNT_NUMBER);
		double withdrawalAmount = 50.21;
		
		// 2-Exercise phase
		transactionController.withdrawAmount(TEST_ACCOUNT_NUMBER, withdrawalAmount);
		double balanceAfter = accountController.getBalance(TEST_ACCOUNT_NUMBER);

		// 3-verify
		assert balanceBefore - withdrawalAmount == balanceAfter;
		if (balanceBefore - withdrawalAmount == balanceAfter)
			TestUtils.printTestPassed("testWithdrawals: TC1 passed");
		else {
			TestUtils.printTestFailed(
					String.format("""
			testWithdrawals: TC1 FAILED XXX: balanceBefore - withdrawalAmount != balanceAfter 
			balanceBefore = %.2f ; withdrawalAmount = %.2f ; balanceAfter = %.2f""", balanceBefore , withdrawalAmount , balanceAfter));
		}
		
		// 4-tear-down
		transactionController.depositAmount(TEST_ACCOUNT_NUMBER, withdrawalAmount);
	}

	
	public static void main(String[] args) {
		// we need to call our test cases (methods)
		testDeposits();
		testDepositsWithInvalidAmount();
		testWithdrawals();
		testWithdrawalsWithInvalidAmount();
		testAggregateBalance();
	
	}

}
