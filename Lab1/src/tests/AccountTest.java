package tests;

import utils.TestUtils;
import model.Account;
import java.util.Date;

public class AccountTest {
	
	public static void testAccountConstructor() {
		
		String test_account_number = "303435";
		String test_username_of_account_holder = "mike";
		String test_account_type = "Standard";
		Date test_account_opening_date = new Date(2025, 02, 15);
		
		Account testAccount = new Account(test_account_number, test_username_of_account_holder, test_account_type, test_account_opening_date);
		
		System.out.println("Starting method");
		
		final String TEST_CASE_ACCOUNT_NUMBER = "TC1-getAccountNumber";
		final String TEST_CASE_USERNAME_OF_ACCOUNT_HOLDER = "TC2-getUsernameOfAccountHolder";
		final String TEST_CASE_ACCOUNT_TYPE = "TC3-getAccountType";
		final String TEST_CASE_ACCOUNT_OPENING_DATE = "TC4-getAccountOpeningDate";
		
	if (testAccount.getAccountNumber() ==  test_account_number) {
        TestUtils.printTestPassed(TEST_CASE_ACCOUNT_NUMBER);
    } else {
        TestUtils.printTestFailed(TEST_CASE_ACCOUNT_NUMBER);
    }
	
    if (testAccount.getUsernameOfAccountHolder() == test_username_of_account_holder) {
         TestUtils.printTestPassed(TEST_CASE_USERNAME_OF_ACCOUNT_HOLDER);
     } else {
         TestUtils.printTestFailed(TEST_CASE_USERNAME_OF_ACCOUNT_HOLDER);
     }

     if (testAccount.getAccountType() == test_account_type) {
         TestUtils.printTestPassed(TEST_CASE_ACCOUNT_TYPE);
     } else {
         TestUtils.printTestFailed(TEST_CASE_ACCOUNT_TYPE);
     }

     if (testAccount.getAccountOpeningDate() == test_account_opening_date) {
         TestUtils.printTestPassed(TEST_CASE_ACCOUNT_OPENING_DATE);
     } else {
         TestUtils.printTestFailed(TEST_CASE_ACCOUNT_OPENING_DATE);
     }

}
	public static void main(String args[]) {
		testAccountConstructor();
	}
}