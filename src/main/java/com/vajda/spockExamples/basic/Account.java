package com.vajda.spockExamples.basic;

import java.math.BigDecimal;

/**
 * Taken from the spock framework examples.
 */
public class Account {
    private BigDecimal balance;

    public Account() {
        this(BigDecimal.ZERO);
    }

    public Account(BigDecimal initial) {
        balance = initial;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidAmountWithdrawnException(amount);
        }
        balance = balance.subtract(amount);
    }
}
