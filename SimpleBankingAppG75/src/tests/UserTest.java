package tests;

import model.User;
import utils.TestUtils;

public class UserTest {

    public static void testUserConstructor() {

        String test_username = "mike";
        String test_password = "my_passwd";
        String test_first_name = "Mike";
        String test_last_name = "Smith";

        User testUser =  new User(test_username, test_password, test_first_name, test_last_name, test_last_name);

        System.out.println("Starting the assertions of the test method: testUserContructor");

        if (testUser.getUsername() ==  test_username) 
            System.out.println(TestUtils.TEXT_COLOR_GREEN + "TC1-getUsername-Passed" + TestUtils.TEXT_COLOR_RESET);
        else
        System.out.println(TestUtils.TEXT_COLOR_RED + "TC1-getUsername-Failed" + TestUtils.TEXT_COLOR_RESET);
    }
    
    public static void main(String args[]) {
    	testUserConstructor();
    }

    
}