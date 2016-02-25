package com.vajda.spockExamples.basic

import com.vajda.spockExamples.basic.Account;
import com.vajda.spockExamples.basic.InvalidAmountWithdrawnException;

import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll


class AccountSpec extends Specification {

    def "create account with initial balance"() {
        given:
        def account = new Account(3)
        
        expect:
        account.balance == 3
    }

    def "withdraw some amount"() {
        given:
        def account = new Account(4.2)

        when:
        account.withdraw(3)

        then:
        account.balance == 1.2
    }
    
    def "exception should be thrown when negative amount is withdrawn"() {
        given:
        def account = new Account(3.5)

        when:
        account.withdraw(-1)

        then:
        thrown(InvalidAmountWithdrawnException)
    }

    def "exception should be thrown when negative amount is withdrawn with more info"() {
        given:
        def account = new Account(3.5)

        when:
        account.withdraw(-1)

        then:
        InvalidAmountWithdrawnException e = thrown()
        e.amount == -1
    }

    def "new account is increased by 3 from old account"() {
        given:
        def account = new Account(3.5)

        when:
        account.withdraw(2)

        then:
        old(account.balance) == account.balance + 2
    }
    
    @Ignore
    def "ignored test"() {
        given:
        def account = new Account(4.1)

        when:
        account.withdraw(3)

        then:
        account.balance == 1.2
    }
    
    @Unroll
    def "when #amount is withdrawn from #startBalance it should remain #endBalance"() {
        given:
        def account = new Account(startBalance)

        when:
        account.withdraw(amount)

        then:
        account.balance == endBalance
        
        where:
        startBalance | amount | endBalance
        4.2          | 2      | 2.2
        5            | 1      | 4
        3            | 3      | 0
    }
}
