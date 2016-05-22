package com.vajda.spockExamples;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import com.vajda.spockExamples.basic.Account;

public class AccountTest {

	@Test
	public void createAccountWithInitialBalance() {
		// GIVEN
		Account account = new Account(new BigDecimal("3"));

		// THEN
		assertEquals(new BigDecimal("3"), account.getBalance());
	}

	@Test
	public void withdrawSomeAmount() {
		// GIVEN
		Account account = new Account(new BigDecimal("3"));

		// WHEN
		account.withdraw(new BigDecimal("1.3"));

		// THEN
		assertEquals(new BigDecimal("1.7"), account.getBalance());
	}

	@Test
	public void withdrawSomeAmountFailing() {
		// GIVEN
		Account account = new Account(new BigDecimal("3"));

		// WHEN
		account.withdraw(new BigDecimal("1.3"));

		// THEN
		assertEquals(new BigDecimal("1.5"), account.getBalance());
	}
}
