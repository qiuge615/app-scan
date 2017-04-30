import org.zaproxy.clientapi.core.ClientApi;

/**
 * Created by speed on 4/18/2017.
 */
public class SecurityTest {

    static final String ZAP_SESSION_IP = "127.0.0.1";
    static final int ZAP_SESSION_PORT = 8090;
    static final String ZAP_HOSTNAME = "localhost";
    static final String ZAP_URI = "http://localhost";
    static final String ZAP_URI_PORT = "http://localhost:8080/bodgeit";
//    static final String SELENIUM_URI = "http://localhost:8888/cgi-bin/TestSecure/index.pl";

    public static void main(String[] args) {
        ZapTools zap = new ZapTools();
//        if( zap.startZAP() == false ) {
//            System.out.println( "ZAP failed to start. Terminating..." );
//            System.exit(0);
//        }
//        zap.startSession( ZAP_HOSTNAME, ZAP_SESSION_PORT );
//        SeleniumTools selenium = new SeleniumTools();
//        if( selenium.startService() == false ) {
//            System.out.println( "Selenium failed to start. Terminating..." );
//            System.exit(0);
//        }

        //We now have ZAP and Selenium running and ready to go:
        //1. open up the Secure Test App and complete the form with Selenium
        //2. spider the Secure Test App (not necessary as there's only 2 URLs in this Secure Test App - demonstration purposes)
        //3. run an active scan to uncover any vulnerabilities
        //4. check for any errors/warning found in the active scan
        //5. save session for later use
        //6. stop and close Selenium
        //7. stop and close ZAP

        // 1
//        WebDriver driver = selenium.getWebDriver();
//        driver.get( SELENIUM_URI );
//        WebElement username = driver.findElement(By.name( "username" ));
//        username.sendKeys("smitty");
//        username.submit();
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // 2
        ClientApi api = new ClientApi( ZAP_HOSTNAME, ZAP_SESSION_PORT );
        if( zap.spider( api, ZAP_URI_PORT ) == false ) {
            System.out.println( "Spider Failed - see console for details. Continuing..." );
        }

        // 3
        if( zap.ascan( api, ZAP_URI_PORT ) == false ) {
            System.out.println( "Active Scan Failed - see console for details. Continuing..." );
        }

        // 4
        System.out.println( zap.checkErrors( api ) );

        // 5
        zap.saveSession (api, "secure" );

        // 6
//        driver.close();
//        selenium.stopService();

        // 7
        zap.stopZAP( ZAP_SESSION_IP, ZAP_SESSION_PORT );
    }

}
