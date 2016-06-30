package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MaxiSavingsAccount extends Account {

    public MaxiSavingsAccount() {}

    public AccountType getAccountType() {
        return AccountType.MAXI_SAVINGS;
    }

    @Override
    public String getAccountDescription(){
        return "Maxi Savings Account";
    }

    @Override
    public double calculateInterest(double amount) {
        // interest rate of 5% assuming no withdrawals in the past 10 days otherwise 0.1%
        if (wasWithdrawnInPastTenDays()) {
            return amount * 0.001;
        } else {
            return amount * .05;
        }
    }

    private boolean wasWithdrawnInPastTenDays() {
        Date today = DateProvider.getInstance().now();

        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DATE, -10);
        Date tenDaysAgo = cal.getTime();

        List<Transaction> withdrawlTransactions =
                getWithdrawlTransactionsBetween(tenDaysAgo, today);
        return !withdrawlTransactions.isEmpty();
    }

}
