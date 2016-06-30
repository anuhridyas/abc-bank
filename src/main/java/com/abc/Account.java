package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.lang.Math.abs;

public abstract class Account {

    public static enum AccountType {
        CHECKING, SAVINGS, MAXI_SAVINGS
    };
    private List<Transaction> transactions;

    public Account() {
        this.transactions = new ArrayList<Transaction>();
    }

    abstract public AccountType getAccountType();

    abstract public String getAccountDescription();

    abstract protected double calculateInterest(double amount);

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void deposit(double amount) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
        }
        //todo: handle overdraft logic here
    }

    public double interestEarned() {
        double amount = sumTransactions();
        return calculateInterest(amount);
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getAmount();
        return amount;
    }

    protected List<Transaction> getWithdrawlTransactionsBetween(Date startDate, Date endDate)
            throws IllegalArgumentException {
        if (startDate.compareTo(endDate) > 0){
            throw new IllegalArgumentException("start date must be before end date");
        }

        List<Transaction> result = new ArrayList<Transaction>();
        for (Transaction t : transactions) {
            Date dt = t.getTransactionDate();
            if(t.getAmount() < 0 && (startDate.compareTo(dt)<=0 && endDate.compareTo(dt)>=0)) {
                result.add(t);
            }
        }
        return result;
    }

    public String statement() {
        StringBuilder sb = new StringBuilder();

        //Translate to pretty account type
        sb.append(getAccountDescription() + "\n");

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : transactions) {
            sb.append("  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + Util.toDollars(t.getAmount()) + "\n");
            total += t.getAmount();
        }
        sb.append("Total " + Util.toDollars(total));
        return sb.toString();
    }
}
