package account.project

import grails.testing.mixin.integration.Integration
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

@Integration(applicationClass = Application.class)
class AccountServiceSpec extends Specification implements ServiceUnitTest<AccountService>{

    AccountService accountService

    def setup() {

        accountService = new AccountService()
    }

    def cleanup() {
    }

    void "Test account creation"() {
        when: "Create an account"
        def result = accountService.save(avaiableCreditLimit, avaiableWithdrawalLimit)

        then: "Result should be as expected"
        result == expectedResult

        where:
        avaiableCreditLimit | avaiableWithdrawalLimit | expectedResult
        5000 | 5000 | "SUCCESS"
        -100 | 1000 | "CREDIT_LIMIT_INVALID"
        1000 | -100 | "WITHDRAWAL_LIMIT_INVALID"
        -100 | -100 | "ALL_LIMIT_INVALID"
    }

    void "Test account update"() {
        given: "create an account"
        Account account = new Account(avaiableWithdrawalLimit: 1000, avaiableCreditLimit: 1000)
        account.save()
        Long idAccount = account.id

        when: "Update an account"
        def result = accountService.save(avaiableCreditLimit, avaiableWithdrawalLimit, idAccount)

        then: "Result should be as expected"
        result == expectedResult

        where:
        avaiableCreditLimit | avaiableWithdrawalLimit | expectedResult
        2000 | 1000 | "SUCCESS"
        -150 | 1200 | "CREDIT_LIMIT_INVALID"
        1000 | -1770 | "WITHDRAWAL_LIMIT_INVALID"
        -2000 | -1500 | "ALL_LIMIT_INVALID"

    }
}
