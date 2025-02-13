package tests;

import model.User;

public class UserTest {


    public static void testUserConstructorWithAsserts() {

        String test_username = "mike";
        String test_password = "my_passwd";
        String test_first_name = "Mike";
        String test_last_name = "Smith";
        String test_mobile_number = "07771234567";

        User testUser =  new User(test_username, test_password, test_first_name, test_last_name, test_mobile_number);

    }
    public static void testUserConstructor() {

        String test_username = "mike";
        String test_password = "my_passwd";
        String test_first_name = "Mike";
        String test_last_name = "Smith";
        String test_mobile_number = "07771234567";

        User testUser =  new User(test_username, test_password, test_first_name, test_last_name, test_mobile_number);

        System.out.println("Starting the assertions of the test method: testUserContructor");
       
        final String TEST_CASE_USERNAME = "TC1-getUsername";
        final String TEST_CASE_PASSWORD = "TC2-getPassword";
        final String TEST_CASE_FIRST_NAME = "TC3-getFirstName";
        final String TEST_CASE_LAST_NAME = "TC4-getLastName";
        final String TEST_CASE_MOBILE_NUMBER = "TC5-getMobileNumber";

        // if (testUser.getUsername() ==  test_username) {
        //     TestUtils.printTestPassed(TEST_CASE_USERNAME);
        // }
        // else {
        //     TestUtils.printTestFailed(TEST_CASE_USERNAME);
        // }
        // if (testUser.getPassword() == test_password) {
        //     TestUtils.printTestPassed(TEST_CASE_PASSWORD);
        // } else {
        //     TestUtils.printTestFailed(TEST_CASE_PASSWORD);
        // }

        // if (testUser.getFirstName() == test_first_name) {
        //     TestUtils.printTestPassed(TEST_CASE_FIRST_NAME);
        // } else {
        //     TestUtils.printTestFailed(TEST_CASE_FIRST_NAME);
        // }

        // if (testUser.getLastName() == test_last_name) {
        //     TestUtils.printTestPassed(TEST_CASE_LAST_NAME);
        // } else {
        //     TestUtils.printTestFailed(TEST_CASE_LAST_NAME);
        // }

        // if (testUser.getMobileNumber() == test_mobile_number) {
        //     TestUtils.printTestPassed(TEST_CASE_MOBILE_NUMBER);
        // } else {
        //     TestUtils.printTestFailed(TEST_CASE_MOBILE_NUMBER);
        // }

        assert  testUser.getUsername() ==  test_username;
        assert  testUser.getPassword() == test_password;
        assert  testUser.getFirstName() == test_first_name;
        assert  testUser.getLastName() == test_last_name;
        assert  testUser.getMobileNumber() == test_mobile_number;

        System.out.println("All Java assertions in the test suite passed (none failed).");


    }
    
    public static void main(String args[]) {
    	testUserConstructor();
    }

    
}