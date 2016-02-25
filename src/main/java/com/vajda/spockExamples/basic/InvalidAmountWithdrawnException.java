package com.vajda.spockExamples.basic;

import java.math.BigDecimal;

/**
 * Taken from the spock framework examples.
 */
public class InvalidAmountWithdrawnException extends RuntimeException {

    private final BigDecimal amount;

    public InvalidAmountWithdrawnException(BigDecimal amount) {
        super("Cannot withdraw " + amount);
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
