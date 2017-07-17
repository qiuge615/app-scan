package com.teknosys.appscan

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TestResultController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond TestResult.list(params), model:[testResultCount: TestResult.count()]
    }

    def show(TestResult testResult) {
        respond testResult
    }

    def create() {
        respond new TestResult(params)
    }

    @Transactional
    def save(TestResult testResult) {
        if (testResult == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (testResult.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond testResult.errors, view:'create'
            return
        }

        testResult.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'testResult.label', default: 'TestResult'), testResult.id])
                redirect testResult
            }
            '*' { respond testResult, [status: CREATED] }
        }
    }

    def edit(TestResult testResult) {
        respond testResult
    }

    @Transactional
    def update(TestResult testResult) {
        if (testResult == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (testResult.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond testResult.errors, view:'edit'
            return
        }

        testResult.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'testResult.label', default: 'TestResult'), testResult.id])
                redirect testResult
            }
            '*'{ respond testResult, [status: OK] }
        }
    }

    @Transactional
    def delete(TestResult testResult) {

        if (testResult == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        testResult.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'testResult.label', default: 'TestResult'), testResult.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'testResult.label', default: 'TestResult'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
