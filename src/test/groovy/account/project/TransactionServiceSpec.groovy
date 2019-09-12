package account.project

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class TransactionServiceSpec extends Specification implements ServiceUnitTest<TransactionService>{

    TransactionService transactionService

    def setup() {
    }

    def cleanup() {
    }

    void "Test transaction (CASH, INSTALLMENT and WITHDRAWAL) is successful"() {
        when: "Create a transaction"
        Account account = new Account(avaiableWithdrawalLimit: 5000, avaiableCreditLimit: 5000)
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
        Account account = new Account(avaiableWithdrawalLimit: 500, avaiableCreditLimit: 500)
        def result = transactionService.createTransaction(account, transactionValue, operationType)

        then: "Check if creation result is what's expected"
        result == expectedResult

        where:
        transactionValue | operationType | expectedResult
        -300 | OperationType.CREDIT_CASH | "SUCCESS"
        -300 | OperationType.CREDIT_INSTALLMENT | "OUT_OF_CREDIT_LIMIT"
        -200 | OperationType.WITHDRAWAL | "SUCCESS"
        -400 | OperationType.WITHDRAWAL | "OUT_OF_WITHDRAWAL_LIMIT"
    }
}
