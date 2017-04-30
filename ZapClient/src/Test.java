import org.zaproxy.clientapi.core.Alert;
import org.zaproxy.clientapi.core.ClientApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by speed on 4/18/2017.
 */
public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO List
        // High priority
        // * Start ZAP in background - task still waits! } Need docs?
        // * Get checkAlerts to work with inner elements }
        // Medium - tidy up
        // * Create min zapapi.jar
        // * Correct way of installing in Eclipse
        // Docs etc
        // * Full wave reg test
        // * Full wavsep reg test
        // * Documentation
        // Publicise
        // * Blog, tweet etc etc
        // * Work out priorities for extending api
        // * Complete tasks - more for internal use than anything else

        List<Alert> ignoreAlerts = new ArrayList<>(2);
//        ignoreAlerts.add(new Alert("Cookie set without HttpOnly flag", null, Alert.Risk.Low, Alert.Confidence.Medium, null, null));
//        ignoreAlerts.add(new Alert(null, null, Alert.Risk.Low, Alert.Confidence.Medium, null, null));

        try {
            (new ClientApi("localhost", 8090)).checkAlerts(ignoreAlerts, null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        List<Alert> requireAlerts = new ArrayList<>(1);
        //ignoreAlerts.add(new Alert(null, null, null, null, null, null));
//        requireAlerts.add(new Alert("Not present", null, Alert.Risk.Low, Alert.Confidence.Medium, null, null));
//        try {
//            ClientApi ca = new ClientApi("localhost", 8090);
//            ca.spider.scan("http://localhost:8080/bodgeit/", "3", "true", "test", "false");
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }

}

