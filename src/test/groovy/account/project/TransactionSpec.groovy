package account.project

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class TransactionSpec extends Specification implements DomainUnitTest<Transaction> {

    Transaction transaction = new Transaction(account: new Account(avaiableCreditLimit: 5000, avaiableWithdrawalLimit: 5000))

    def setup() {
    }

    def cleanup() {
    }

    void "Test if transaction is persisted with right values"() {
        when: "Right values are set"
        transaction.operationType = operationType
        transaction.amount = amount
        transaction.balance = balance
        transaction.eventDate = eventDate
        transaction.dueDate = dueDate
        transaction.save()

        then: "Transaction should be persisted, should count return 1"
        Transaction.count() == 1

        where:
        operationType                    | amount | balance | eventDate  | dueDate
        OperationType.CREDIT_CASH        | -50    | -50     | new Date() | new Date() + 30
        OperationType.CREDIT_INSTALLMENT | -100   | -100    | new Date() | new Date() + 60
        OperationType.WITHDRAWAL         | -20    | -20     | new Date() | new Date() + 30
        OperationType.PAYMENT            | 150    | 150     | new Date() | new Date()
    }


    void "Test if transaction  withdrawal/credit fields scale less than or equal 2"() {
        when: "A wrong value with scale bigger than 2 is set"

        transaction.operationType = operationType
        transaction.amount = amount
        transaction.balance = balance
        transaction.eventDate = new Date()
        transaction.dueDate = new Date() + 30
        transaction.save()

        then: "The attributes should be scale equal 2 or less"
        transaction.amount == expectedAmount && transaction.balance == expectedBalance

        where:
        operationType                    | amount | balance |  expectedAmount | expectedBalance
        OperationType.CREDIT_CASH        | -50.333 | -50.333 | -50.33 | -50.33
        OperationType.CREDIT_INSTALLMENT | -100.39213123 | -100.39213123 | -100.39 | -100.39
        OperationType.WITHDRAWAL         | -20.9998 | -20.9998 | -21 | -21
        OperationType.PAYMENT            | 150.95831 | 150.95831 | 150.96 | 150.96
    }

    void "Test if transaction with dueDate less than eventDate is NOT persisted" () {
        when: "A transaction with dueDate less than eventDate is set"

        transaction.operationType = OperationType.CREDIT_CASH
        transaction.amount = -50
        transaction.balance = -50
        transaction.eventDate = eventDate
        transaction.dueDate = dueDate
        transaction.save()

        then: "An error should be found"
        transaction.errors["dueDate"]?.codes?.contains("date.toosmall")

        where:
        eventDate | dueDate
        new Date() | new Date () - 5
        new Date() | new Date () - 10
        new Date() + 8 | new Date ()

    }

}
