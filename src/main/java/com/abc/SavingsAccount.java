package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SavingsAccount extends Account {

    public SavingsAccount() {}

    public AccountType getAccountType() {
        return AccountType.SAVINGS;
    }

    @Override
    public String getAccountDescription(){
        return "Savings Account";
    }

    @Override
    public double calculateInterest(double amount) {
        if (amount <= 1000)
            return amount * 0.001;
        else
            return 1 + (amount-1000) * 0.002;
    }
}
