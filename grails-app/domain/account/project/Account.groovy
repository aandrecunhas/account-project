package account.project

class Account {

    BigDecimal avaiableCreditLimit
    BigDecimal avaiableWithdrawalLimit

    static transients = ['transactions', 'balance']

    static constraints = {
        avaiableWithdrawalLimit range: 0..1000000, scale: 2
        avaiableCreditLimit range: 0..1000000, scale: 2
    }

    def getTransactions() {
        return Transaction.findAllByAccount(this)
    }

    def getBalance() {
        return this.transactions?.balance?.sum() ?:0
    }

    def getTransactions(OperationType operationType) {
        return this.transactions.findAll { it -> it.operationType == operationType }
    }

    def getBalance(OperationType operationType) {
        return this.getTransactions(operationType)?.balance?.sum() ?:0
    }
}
