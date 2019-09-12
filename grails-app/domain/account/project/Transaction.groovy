package account.project

class Transaction {

    OperationType operationType
    BigDecimal amount
    BigDecimal balance
    Date eventDate
    Date dueDate
    Account account

    static constraints = {
        amount scale: 2
        balance scale: 2, validator: { val, obj, errors ->
            if(obj.operationType == OperationType.WITHDRAWAL && (obj.account.getBalance() + obj.account.avaiableCreditLimit + obj.account.avaiableWithdrawalLimit + val) < 0) {
                errors.rejectValue('balance', 'balance.noLimitWithdrawal')
                return false
            }

            if([OperationType.CREDIT_CASH, OperationType.CREDIT_INSTALLMENT].contains(obj.operationType) && (obj.account.getBalance() + obj.account.avaiableCreditLimit + val) < 0) {
                errors.rejectValue('balance', 'balance.noLimitCredit')
                return false
            }

            return true
        }
        dueDate validator: { val, obj, errors ->
            if(val < obj.eventDate) {
                errors.rejectValue('dueDate', 'date.toosmall')
                return false
            }
            return true
        }
    }
}
