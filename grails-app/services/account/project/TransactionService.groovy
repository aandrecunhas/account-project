package account.project

import grails.gorm.transactions.Transactional

@Transactional
class TransactionService {

    def createTransaction(Account account, BigDecimal transactionValue, OperationType operationType) {
        if(save(account, transactionValue, operationType)) return "SUCCESS"

        return "ERROR"
    }


    def save(Account account, BigDecimal transactionValue, OperationType operationType) {
        def balanceAdjusted = adjust(account, transactionValue, operationType)
        println balanceAdjusted
        Transaction transaction = new Transaction()
        transaction.operationType = operationType
        transaction.account = account
        transaction.balance = balanceAdjusted
        transaction.amount = transactionValue
        transaction.eventDate = new Date()
        transaction.dueDate = new Date()
        transaction.save(failOnError: true)
    }


    def adjust(Account account, BigDecimal transactionValue, OperationType operationType) {
        if([OperationType.CREDIT_CASH, OperationType.CREDIT_INSTALLMENT, operationType.WITHDRAWAL].contains(operationType)) {
            def paymentTransactions = account.getTransactions(OperationType.PAYMENT)
            def index = 0
            def balanceAdjusted = transactionValue

            while(account.getBalance(OperationType.PAYMENT) > 0 && balanceAdjusted < 0){
                def result = balanceAdjusted + paymentTransactions[index].balance
                if(result <= 0) {
                    balanceAdjusted = result
                    paymentTransactions[index].balance = 0
                } else {
                    balanceAdjusted = 0
                    paymentTransactions[index].balance = result
                }
                index++
            }
            return balanceAdjusted
        }

        if(OperationType.PAYMENT == operationType) {
            def transactions = account.getTransactions(OperationType.CREDIT_CASH) + account.getTransactions(OperationType.CREDIT_INSTALLMENT) + account.getTransactions(OperationType.WITHDRAWAL)
            transactions = transactions.sort { it.operationType.chargerOrder }

            def index = 0
            def balanceAdjusted = transactionValue

            while(account.getBalance(OperationType.CREDIT_CASH) + account.getBalance(OperationType.CREDIT_INSTALLMENT) + account.getBalance(OperationType.WITHDRAWAL) < 0 && balanceAdjusted > 0){
                def result = balanceAdjusted + transactions[index].balance
                if(result >= 0) {
                    balanceAdjusted = result
                    transactions[index].balance = 0
                } else {
                    balanceAdjusted = 0
                    transactions[index].balance = result
                }
                index++
            }
            return balanceAdjusted
        }
    }
}
