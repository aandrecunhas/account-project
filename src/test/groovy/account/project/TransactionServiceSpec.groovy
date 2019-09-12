package account.project

import grails.testing.mixin.integration.Integration
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

@Integration(applicationClass = Application.class)
class TransactionServiceSpec extends Specification implements ServiceUnitTest<TransactionService>{

    TransactionService transactionService

    def setup() {
        transactionService = new TransactionService()
    }

    def cleanup() {
    }

    void "Test transaction (CASH, INSTALLMENT and WITHDRAWAL) is successful"() {
        when: "Create a transaction"
        Account account
        Account.withTransaction {
            account = new Account(avaiableWithdrawalLimit: 5000, avaiableCreditLimit: 5000)
            account.save()
        }
        def result = transactionService.createTransaction(account, transactionValue, operationType)

        then: "Check Transaction creation"
        result == "SUCCESS"

        where:
        transactionValue | operationType
        -100 | OperationType.CREDIT_CASH
        -100 | OperationType.CREDIT_INSTALLMENT
        -100 | OperationType.WITHDRAWAL
    }

    void "Test transaction (CASH, INSTALLMENT and WITHDRAWAL) fails limit"() {
        when: "Create a transaction that surpass limit"
        Account account
        Account.withTransaction {
            account = new Account(avaiableWithdrawalLimit: 500, avaiableCreditLimit: 500)
            account.save()
        }
        def result = transactionService.createTransaction(account, transactionValue, operationType)

        then: "Check if creation result is what's expected"
        result == expectedResult

        where:
        transactionValue | operationType | expectedResult
        -300 | OperationType.CREDIT_CASH | "SUCCESS"
        -300 | OperationType.CREDIT_INSTALLMENT | "ERROR"
        -200 | OperationType.WITHDRAWAL | "SUCCESS"
        -400 | OperationType.WITHDRAWAL | "ERROR"
    }
}
