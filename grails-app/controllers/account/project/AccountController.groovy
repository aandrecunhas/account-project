package account.project

import grails.converters.JSON
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

class AccountController {

    def accountService

    def create() {
        def jsonRequest = request.JSON
        def result = accountService.save(new BigDecimal(jsonRequest.available_credit_limit.amount), new BigDecimal(jsonRequest.available_withdrawal_limit.amount))
        if(result == "SUCCESS") {
            render contentType: 'application/json', status: HttpStatus.OK, text: [message: result] as JSON
        } else {
            render contentType: 'application/json', status: HttpStatus.UNPROCESSABLE_ENTITY, text: [message: result] as JSON
        }
    }

    @RequestMapping(method = RequestMethod.PATCH)
    def update(Long id){
        def jsonRequest = request.JSON
        def result = accountService.save(new BigDecimal(jsonRequest.available_credit_limit.amount), new BigDecimal(jsonRequest.available_withdrawal_limit.amount), id)
        if(result == "SUCCESS") {
            render contentType: 'application/json', status: HttpStatus.OK, text: [message: result] as JSON
        } else {
            render contentType: 'application/json', status: HttpStatus.UNPROCESSABLE_ENTITY, text: [message: result] as JSON
        }
    }

    def limits() {

        def map = accountService.showAll()

        render contentType: 'application/json', map as JSON
    }
}
