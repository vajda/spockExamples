package com.vajda.spockExamples.basic;

import java.math.BigDecimal;

/**
 * Taken from the spock framework examples.
 */
public class InvalidAmountWithdrawnException extends RuntimeException {

    private static final long serialVersionUID = -7809544826023035323L;

    private final BigDecimal amount;

    public InvalidAmountWithdrawnException(BigDecimal amount) {
        super("Cannot withdraw " + amount);
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
