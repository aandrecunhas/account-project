package account.project

import grails.converters.JSON
import org.springframework.http.HttpStatus

class TransactionController {

    def transactionService

    def createTransaction() {
        def jsonRequest = request.JSON
        def accountId = jsonRequest.account_id as Long
        def operationType = OperationType.valueOf(jsonRequest.operation_type)
        def amount = new BigDecimal(jsonRequest.amount)

        Account account = Account.get(accountId)

        def result = transactionService.createTransaction(account, amount, operationType)

        if(result == "SUCCESS") {
            render contentType: 'application/json', status: HttpStatus.OK, text: [message: result] as JSON
        } else {
            render contentType: 'application/json', status: HttpStatus.UNPROCESSABLE_ENTITY, text: [message: result] as JSON
        }
    }

    def createPayment() {
        def jsonRequest = request.JSON
        def results = []
        jsonRequest.each { request ->
            def accountId = request.account_id as Long
            def amount = new BigDecimal(request.amount)

            Account account = Account.get(accountId)

            results << transactionService.createTransaction(account, amount, OperationType.PAYMENT)
        }

        if(results.contains("ERROR")) {
            render contentType: 'application/json', status: HttpStatus.UNPROCESSABLE_ENTITY, text: [message: "ERROR"] as JSON
        } else {
            render contentType: 'application/json', status: HttpStatus.OK, text: [message: "SUCCESS"] as JSON
        }
    }
}
