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
        balance scale: 2
        dueDate validator: { val, obj, errors ->
            if(val < obj.eventDate) {
                errors.rejectValue('dueDate', 'date.toosmall')
                return false
            }
            return true
        }
    }
}
