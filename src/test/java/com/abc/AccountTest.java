package com.abc;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void accountCreation() {
        Account checkingAccount = new CheckingAccount();
        assertEquals(checkingAccount.getAccountType(), Account.AccountType.CHECKING);

        Account savingsAccount = new SavingsAccount();
        assertEquals(savingsAccount.getAccountType(), Account.AccountType.SAVINGS);

        Account maxiSavingsAccount = new MaxiSavingsAccount();
        assertEquals(maxiSavingsAccount.getAccountType(), Account.AccountType.MAXI_SAVINGS);
    }

    @Test
    public void testPositiveDeposit() {
        Account checkingAccount = new CheckingAccount();
        checkingAccount.deposit(100.0);

        assertEquals(checkingAccount.sumTransactions(), 100.0, DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeDeposit() {
        Account checkingAccount = new CheckingAccount();
        checkingAccount.deposit(-100.0);
    }

    @Test
    public void testPositiveWithdrawl() {
        Account checkingAccount = new CheckingAccount();
        checkingAccount.deposit(100.0);
        checkingAccount.withdraw(20.0);

        assertEquals(checkingAccount.sumTransactions(), 80.0, DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeWithdrawl() {
        Account checkingAccount = new CheckingAccount();
        checkingAccount.withdraw(-100.0);
    }

    @Test
    public void sumTransactions() {
        Account checkingAccount = new CheckingAccount();
        checkingAccount.deposit(100.0);
        checkingAccount.withdraw(20.0);
        checkingAccount.deposit(100.0);
        checkingAccount.withdraw(30.0);
        checkingAccount.deposit(100.0);

        assertEquals(checkingAccount.sumTransactions(), 250.0, DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getWithdrawlTransactionsBetweenInvalidRange() {
        Account checkingAccount = new CheckingAccount();
        checkingAccount.deposit(100.0);

        Date today = DateProvider.getInstance().now();

        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DATE, 1);
        Date tomorrow = cal.getTime();

        checkingAccount.getWithdrawlTransactionsBetween(tomorrow, today);
    }

    @Test
    public void getWithdrawlTransactionsBetween() {
        Account checkingAccount = new CheckingAccount();
        checkingAccount.deposit(100.0);
        Date startDate = DateProvider.getInstance().now();
        checkingAccount.withdraw(50.0);
        checkingAccount.deposit(200.0);

        //todo: can be done better. Use mocking: set mock date to transaction
        try {
            Thread.sleep(2000);
        } catch(Exception e){
            assertTrue(false);
        }
        checkingAccount.withdraw(50.0);
        Date endDate = DateProvider.getInstance().now();

        try {
            Thread.sleep(2000);
        } catch(Exception e){
            assertTrue(false);
        }
        checkingAccount.withdraw(10.0);

        List<Transaction> withdrawlTransactions = checkingAccount.getWithdrawlTransactionsBetween(startDate, endDate);
        assertEquals(withdrawlTransactions.size(), 2);
    }

    @Test
    public void interestEarnedCheckingAccount() {
        Account checkingAccount = new CheckingAccount();
        checkingAccount.deposit(1000.0);

        assertEquals(1.0, checkingAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void interestEarnedSavingsAccount() {
        Account savingsAccount = new SavingsAccount();
        savingsAccount.deposit(1500.0);

        assertEquals(2.0, savingsAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void interestEarnedMaxiSavingsAccountNoWithdrawl() {
        Account maxiSavingsAccount = new MaxiSavingsAccount();
        maxiSavingsAccount.deposit(1000.0);

        assertEquals(50.0, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void interestEarnedMaxiSavingsAccountWithWithdrawl() {
        Account maxiSavingsAccount = new MaxiSavingsAccount();
        maxiSavingsAccount.deposit(2000.0);
        maxiSavingsAccount.withdraw(200.0);
        maxiSavingsAccount.withdraw(200.0);
        maxiSavingsAccount.withdraw(200.0);
        maxiSavingsAccount.withdraw(200.0);
        maxiSavingsAccount.withdraw(200.0);

        assertEquals(1.0, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
    }
}
