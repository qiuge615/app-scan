package com.teknosys.appscan

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TestResultDetailController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond TestResultDetail.list(params), model:[testResultDetailCount: TestResultDetail.count()]
    }

    def show(TestResultDetail testResultDetail) {
        respond testResultDetail
    }

    def create() {
        respond new TestResultDetail(params)
    }

    @Transactional
    def save(TestResultDetail testResultDetail) {
        if (testResultDetail == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (testResultDetail.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond testResultDetail.errors, view:'create'
            return
        }

        testResultDetail.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'testResultDetail.label', default: 'TestResultDetail'), testResultDetail.id])
                redirect testResultDetail
            }
            '*' { respond testResultDetail, [status: CREATED] }
        }
    }

    def edit(TestResultDetail testResultDetail) {
        respond testResultDetail
    }

    @Transactional
    def update(TestResultDetail testResultDetail) {
        if (testResultDetail == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (testResultDetail.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond testResultDetail.errors, view:'edit'
            return
        }

        testResultDetail.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'testResultDetail.label', default: 'TestResultDetail'), testResultDetail.id])
                redirect testResultDetail
            }
            '*'{ respond testResultDetail, [status: OK] }
        }
    }

    @Transactional
    def delete(TestResultDetail testResultDetail) {

        if (testResultDetail == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        testResultDetail.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'testResultDetail.label', default: 'TestResultDetail'), testResultDetail.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'testResultDetail.label', default: 'TestResultDetail'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
