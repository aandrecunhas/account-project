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
        Long idAccount
        given: "create an account"
            Account.withTransaction {
            Account account = new Account(avaiableWithdrawalLimit: 1000, avaiableCreditLimit: 1000)
            account.save()
            idAccount = account.id
        }
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

    void "Test account show"() {
        given: "create an account"
        Account.withTransaction {
            Account account = new Account(avaiableWithdrawalLimit: 1000, avaiableCreditLimit: 1000, id: 1)
            account.save()
        }
        when: "Select an account"
        def result = accountService.show(idAccount)

        then: "Result should be as expected"
        result == expectedResult

        where:
        idAccount | expectedResult
        1 | [id: 1, avaiableWithdrawalLimit: 1000, avaiableCreditLimit: 1000]
        0 | []
    }

    void "Test account show all"() {
        given: "create some accounts"
        Account.withTransaction {
            Account account = new Account(avaiableWithdrawalLimit: 1000, avaiableCreditLimit: 1000, id: 1)
            account.save()

            Account account2 = new Account(avaiableWithdrawalLimit: 2000, avaiableCreditLimit: 2000, id: 2)
            account2.save()

            Account account3 = new Account(avaiableWithdrawalLimit: 3000, avaiableCreditLimit: 3000, id: 3)
            account3.save()
        }
        when: "Select an account"
        def result = accountService.showAll()

        then: "Result should be as expected"
        result == [[id: 1, avaiableWithdrawalLimit: 1000, avaiableCreditLimit: 1000], [id: 2, avaiableWithdrawalLimit: 2000, avaiableCreditLimit: 2000], [id: 3, avaiableWithdrawalLimit: 3000, avaiableCreditLimit: 3000]]

    }


}
