package com.teknosys.appscan

import org.zaproxy.clientapi.core.ClientApi

class SecurityScanService {

    def String scan(String zapAddress, int zapPort) {
        try {
            (new ClientApi(zapAddress, zapPort)).checkAlerts(null, null);
        } catch (Exception e) {
            return e.getMessage()
        }
        return "No issue found"
    }

    def TestResult analysisResult(String resultStr) {
//        new File("C:\\work\\app-scan\\src\\test\\resources\\resp.txt").write(resultStr)
        String[] splits = resultStr.split("\n")
        def result = new TestResult()
        if (splits.length < 2) {
            result.totalAlerts = 0
            result.testResult = true
        } else {
            result.testResult = false
            result.totalAlerts = splits[0].split(" ")[1].toInteger()
            for (int i = 1; i < splits.length - 1; i++) {
//                println("processing " + i + "\t" + splits[i])
                if (!splits[i].contains("Alert: ")) continue
                TestResultDetail detail = new TestResultDetail()
                // TODO will refactor below code later.
                String[] part1 = splits[i].split(", Risk: ")
                detail.title = part1[0].replace("Alert: ", "")

                String[] part2 = part1[1].split(", Confidence: ")
                detail.risk = part2[0].replace("Risk: ", "")

                String[] part3 = part2[1].split(", Url: ")
                detail.confidence = part3[0].replace("Confidence: ", "")

                String[] part4 = part3[1].split(", Param: ")
                detail.url = part4[0].replace("Url: ", "")

                String[] part5 = part4[1].split(", Other: ")
                if (part5.length > 0) detail.param = part5[0].replace("Param: ", "")

                if (part5.length > 1) detail.notes = part5[1]

                result.addToDetails(detail)
            }
        }
        result.executionDate = new Date()
        return result;
    }

}

