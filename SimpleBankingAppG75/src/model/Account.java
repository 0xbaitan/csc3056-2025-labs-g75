package model;

import java.util.Date;

public class Account {
    private String account_number;
    private String username_of_account_holder;
    private String account_type;
    private Date account_opening_date;

public String getAccountNumber() {
    return account_number;
}
public void setAccountnumber(String account_number){
    this.account_number = account_number;
}
public String getUsernameOfAccountHolder() {
    return username_of_account_holder;
}
public void setUsernameOfAccountHolder(String username_of_account_holder) {
    this.username_of_account_holder = username_of_account_holder;
}
public String getAccountType() {
    return account_type;
}
public void setAccountType(String account_type) {
    this.account_type = account_type;
}
public Date getAccountOpeningDate() {
    return account_opening_date;
}
public void setAccountOpeningDate(Date account_opening_date) {
    this.account_opening_date = account_opening_date;
}

public Account(String account_number, String username_of_account_holder, String account_type, Date account_opening_date) {
    super();
    this.account_number = account_number;
    this.username_of_account_holder = username_of_account_holder;
    this.account_type = account_type;
    this.account_opening_date = account_opening_date;
}

public String toString() {
	return account_number + "," + username_of_account_holder + "," + account_type + "," + account_opening_date;
}


}




