package account.project

import grails.gorm.transactions.Transactional

@Transactional
class AccountService {

    def save(BigDecimal avaiableCreditLimit, BigDecimal avaiableWithdrawalLimit, Long idAccount = null) {

        Account account = Account.get(idAccount)

        if(!account) {
            account = new Account()
        }

        account.avaiableCreditLimit = avaiableCreditLimit
        account.avaiableWithdrawalLimit = avaiableWithdrawalLimit

        if(account.save()){
            return "SUCCESS"
        }

        if(account.errors['avaiableCreditLimit'] && account.errors['avaiableWithdrawalLimit']) {
            return "ALL_LIMIT_INVALID"
        }

        if(account.errors['avaiableCreditLimit']) {
            return "CREDIT_LIMIT_INVALID"
        }

        if(account.errors['avaiableWithdrawalLimit']) {
            return "WITHDRAWAL_LIMIT_INVALID"
        }
    }


    def show(Long idAccount) {
        Account account = Account.get(idAccount)

        if(account) {
            return [id: account.id, avaiableCreditLimit: account.avaiableCreditLimit, avaiableWithdrawalLimit: account.avaiableWithdrawalLimit]
        }

        return []
    }

    def showAll() {
        List<Account> accounts = Account.list()
        List<Map> accountListShow = []

        accounts.each { account ->
            accountListShow << show(account.id)
        }

        return accountListShow
    }
}
