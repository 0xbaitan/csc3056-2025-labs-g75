package model;

import java.util.Date;

public class Transaction {
    String account_number;
    double transaction_amount;
    Date transaction_date;

    public Transaction(String account_number, double transaction_amount, Date transaction_date) {
        this.account_number = account_number;
        this.transaction_amount = transaction_amount;
        this.transaction_date = transaction_date;
    }

    public String getAccountNumber() {
        return account_number;
    }

    public double getTransactionAmount() {
        return transaction_amount;
    }


    public Date getTransactionDate() {
        return transaction_date;
    }

    public void setAccountNumber(String account_number) {
        this.account_number = account_number;
    }

    public void setTransactionAmount(double transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

    public void setTransactionDate(Date transaction_date) {
        this.transaction_date = transaction_date;
    }


    
    
}
