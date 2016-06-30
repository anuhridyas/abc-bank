package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;
    //private final String custId="CUST"+System.nanoTime();

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    /*public String getCustId() {
        return custId;
    }*/

    public List<Account> getAccounts() {
        return accounts;
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0.0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    public String getStatement() {
        String statement = "";
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + a.statement() + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + Util.toDollars(total);
        return statement;
    }

    public boolean isMyOwnAccount(Account account) {
        if (accounts.contains(account)) {
            return true;
        }
        return false;
    }

    public void transfer(Account sourceAccount, Account destAccount, double amount) throws IllegalArgumentException {
        if (amount <=0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else if (sourceAccount == destAccount) {
            throw new IllegalArgumentException("Cannot transfer between the same accounts");
        }

        try{
            sourceAccount.withdraw(amount);
            destAccount.deposit(amount);
        } catch(Exception e) {
            throw new IllegalArgumentException("error withdrawing");
        }
    }
}
