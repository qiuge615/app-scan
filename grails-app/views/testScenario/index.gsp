<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'testScenario.label', default: 'TestScenario')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-testScenario" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-testScenario" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <table>
            <thead>
                <tr>
                    <td>Test Scenario</td>
                    <td>Run Security Scan</td>
                </tr>
            </thead>
            <tbody>
              <g:each status="i" in="${testScenarioList}" var="scenario">
                <tr>
                  <td><g:link action="show" id="${scenario.id}">${scenario.testScenarioName}</g:link></td>
                  <td><g:link action="scan" id="${scenario.id}">Scan App</g:link></td>

                </tr>
              </g:each>
            </tbody>
            </table>
        </div>
    </body>
</html>
