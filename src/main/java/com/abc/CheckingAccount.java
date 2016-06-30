package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CheckingAccount extends Account {

    public CheckingAccount() {}

    public AccountType getAccountType() {
        return AccountType.CHECKING;
    }

    @Override
    public String getAccountDescription(){
        return "Checking Account";
    }

    @Override
    public double calculateInterest(double amount) {
        return amount * 0.001;
    }
}