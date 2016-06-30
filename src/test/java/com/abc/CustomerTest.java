package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        henry.totalInterestEarned();

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingAccount());
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingAccount());
        assertNotEquals(3, oscar.getNumberOfAccounts());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidTransferAmount(){
        Account checkingAccount = new CheckingAccount();
        Account savingAccount = new SavingsAccount();

        Customer oscar = new Customer("Oscar").openAccount(checkingAccount);
        oscar.openAccount(savingAccount);

        oscar.transfer(checkingAccount, savingAccount, -5.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferBetweenSameAccounts(){
        Account checkingAccount = new CheckingAccount();
        Customer oscar = new Customer("Oscar").openAccount(checkingAccount);

        oscar.transfer(checkingAccount, checkingAccount, 100.0);
    }

    @Test
    public void totalInterestEarned() {
        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();
        Account maxiSavingsAccount = new MaxiSavingsAccount();

        Customer philip = new Customer("Philip").
                openAccount(checkingAccount).
                openAccount(savingsAccount).
                openAccount(maxiSavingsAccount);

        checkingAccount.deposit(10000.0); // 10
        savingsAccount.deposit(10000.0); // 1+ 18
        maxiSavingsAccount.deposit(10000.0); // 500

        assertEquals(529.0, philip.totalInterestEarned(), DOUBLE_DELTA);
    }
}
