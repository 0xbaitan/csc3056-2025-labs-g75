package tests;

import controller.AccountController;
import controller.TransactionController;
import utils.TestUtils;

public class SimpleBankingAppTestSOLVED {

    
	private static final TransactionController transactionController = TransactionController.getInstance();
	private static final AccountController accountController = AccountController.getInstance();

	
	// this test method (test case) verifies if the Deposit feature works properly
	public static void testDeposits() {
		
		final String TEST_ACCOUNT_NUMBER = "5495-1234";
		
		// 1-Setup phase
		double balanceBefore = accountController.getBalance(TEST_ACCOUNT_NUMBER); 
		double depositAmount = 50.21;
		
		// 2-Exercise phase
		transactionController.addTransaction(TEST_ACCOUNT_NUMBER, depositAmount);
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
		transactionController.addTransaction(TEST_ACCOUNT_NUMBER, -depositAmount);
	}

	// this test method (test case) verifies if the Withdraw feature works properly
	
	public static void testWithdrawals() {
		final String TEST_ACCOUNT_NUMBER = "5495-1234";
		
		// 1-Setup phase
		double balanceBefore = accountController.getBalance(TEST_ACCOUNT_NUMBER);
		double withdrawalAmount = 50.21;
		
		// 2-Exercise phase
		transactionController.addTransaction(TEST_ACCOUNT_NUMBER, -withdrawalAmount);
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
		transactionController.addTransaction(TEST_ACCOUNT_NUMBER, withdrawalAmount);
	}

	
	public static void main(String[] args) {
		// we need to call our test cases (methods)
		testDeposits();
		testWithdrawals();
	}

}
