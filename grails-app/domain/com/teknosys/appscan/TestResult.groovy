package com.teknosys.appscan

class TestResult {
 boolean testResult = true
    Date executionDate
    int totalAlerts = 0
    List details = []

    static constraints = {
    }

    static hasMany = [ details: TestResultDetail ]
}
