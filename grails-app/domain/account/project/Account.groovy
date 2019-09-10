package account.project

class Account {

    BigDecimal avaiableCreditLimit
    BigDecimal avaiableWithdrawalLimit

    static constraints = {
        avaiableWithdrawalLimit range: 0..1000000, scale: 2
        avaiableCreditLimit range: 0..1000000, scale: 2
    }
}
