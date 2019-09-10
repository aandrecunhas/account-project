package account.project

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class AccountSpec extends Specification implements DomainUnitTest<Account> {

    Account account = new Account()

    def setup() {
    }

    def cleanup() {
    }

    void "Test if account is persisted with right values"() {
        when: "Right values are set"
        account.avaiableCreditLimit = 5000
        account.avaiableWithdrawalLimit = 1000
        account.save()

        then: "1 account object should be found"
        Account.count() == 1
    }

    void "Test if account is NOT persisted with withdrawal limit/credit limit wrong range (0-1kk)"() {
        when: "A wrong value in avaiableWithdrawalLimit/avaiableCreditLimit is set"
        account.avaiableCreditLimit = avaiableCreditLimit
        account.avaiableWithdrawalLimit = avaiableWithdrawalLimit
        account.save()

        then: "A range error should be found"
        account.errors[wrongAttribute]?.codes?.contains(expectedError)

        where:
        avaiableWithdrawalLimit | avaiableCreditLimit | wrongAttribute | expectedError
        -1 | 5000 | "avaiableWithdrawalLimit" | "range.toosmall"
        -3000 | 5000 | "avaiableWithdrawalLimit" | "range.toosmall"
        1000001 | 5000 | "avaiableWithdrawalLimit" | "range.toobig"
        2000000 | 5000 | "avaiableWithdrawalLimit" | "range.toobig"
        1000 | -1 | "avaiableCreditLimit" | "range.toosmall"
        1000 | -3000 | "avaiableCreditLimit" | "range.toosmall"
        1000 | 1000001 | "avaiableCreditLimit" | "range.toobig"
        1000 | 2000000 | "avaiableCreditLimit" | "range.toobig"
    }

    void "Test if account withdrawal/credit limit scale less than or equal 2"() {
        when: "A wrong value with scale bigger than 2 is set"
        account.avaiableCreditLimit = avaiableCreditLimit
        account.avaiableWithdrawalLimit = avaiableWithdrawalLimit
        account.save()

        then: "The attributes should be scale equal 2 or less"
        account.avaiableWithdrawalLimit == expectedAvaiableWithdrawalLimit && account.avaiableCreditLimit == expectedAvaiableCreditLimit

        where:
        avaiableWithdrawalLimit | avaiableCreditLimit | expectedAvaiableWithdrawalLimit | expectedAvaiableCreditLimit
        200.333 | 200.992 | 200.33 | 200.99
        932.93832 | 200.99553 | 932.94 | 201
        200.33143678 | 1233.33143678 | 200.33 | 1233.33
        1.99 | 500.55 | 1.99 | 500.55

    }

}
