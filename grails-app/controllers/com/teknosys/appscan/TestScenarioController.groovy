package com.teknosys.appscan

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TestScenarioController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def securityScanService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond TestScenario.list(params), model:[testScenarioCount: TestScenario.count()]
    }

    def show(TestScenario testScenario) {
        respond testScenario
    }

    def create() {
        respond new TestScenario(params)
    }

    def scan(TestScenario testScenario) {
        String result = securityScanService.scan("localhost", 8090)
        TestResult tr = securityScanService.analysisResult(result)
        testScenario.addToTestResults(tr)
        testScenario.save flush:true
        render result
    }

    @Transactional
    def save(TestScenario testScenario) {
        if (testScenario == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (testScenario.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond testScenario.errors, view:'create'
            return
        }

        testScenario.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'testScenario.label', default: 'TestScenario'), testScenario.id])
                redirect testScenario
            }
            '*' { respond testScenario, [status: CREATED] }
        }
    }

    def edit(TestScenario testScenario) {
        respond testScenario
    }

    @Transactional
    def update(TestScenario testScenario) {
        if (testScenario == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (testScenario.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond testScenario.errors, view:'edit'
            return
        }

        testScenario.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'testScenario.label', default: 'TestScenario'), testScenario.id])
                redirect testScenario
            }
            '*'{ respond testScenario, [status: OK] }
        }
    }

    @Transactional
    def delete(TestScenario testScenario) {

        if (testScenario == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        testScenario.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'testScenario.label', default: 'TestScenario'), testScenario.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'testScenario.label', default: 'TestScenario'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
