package com.teknosys.appscan

class TestScenario {
String testScenarioName = ""

    static constraints = {
    }

    List testResults = []

    static hasMany = [ testResults: TestResult]
}
