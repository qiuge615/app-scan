import org.zaproxy.clientapi.core.Alert;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;
import org.zaproxy.clientapi.core.ClientApiMain;
import org.zaproxy.clientapi.gen.Spider;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by speed on 4/18/2017.
 */
public class ZapTools {

    String ZAP_LOCATION = "C:\\dev\\Zed\\";
    String SAVE_SESSION_DIRECTORY = "ZAPSessions\\";

    public boolean startZAP() {
        try {
            String[] command = { "CMD", "/C", this.ZAP_LOCATION + "ZAP.exe" };
            ProcessBuilder proc = new ProcessBuilder(command);
            proc.directory(new File(this.ZAP_LOCATION));
            Process p = proc.start();
            p.waitFor();
            BufferedReader input = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            OutputStreamWriter oStream = new OutputStreamWriter(
                    p.getOutputStream());
            oStream.write("process where name='ZAP.exe'");
            oStream.flush();
            oStream.close();
            String line;
            while ((line = input.readLine()) != null) {
                //kludge to tell when ZAP is started and ready
                if (line.contains("INFO") && line.contains("org.parosproxy.paros.control.Control") && line.contains("New Session")) {
                    input.close();
                    break;
                }
            }
            System.out.println("ZAP has started successfully.");
            return true;
        } catch (Exception ex) {
            System.out.println("ZAP was unable to start.");
            ex.printStackTrace();
            return false;
        }
    }

    public void stopZAP(String zapaddr, int zapport) {
        ClientApiMain.main(new String[] { "stop", "zapaddr=" + zapaddr,	"zapport=" + zapport });
    }

    public void startSession(String zapaddr, int zapport) {
        ClientApiMain.main(new String[] { "newSession", "zapaddr=" + zapaddr, "zapport=" + zapport });
        System.out.println( "session started" );
        System.out.println("Session started successfully.");
    }

    public void saveSession(ClientApi api, String fileName) {
        try {
            String path = this.SAVE_SESSION_DIRECTORY + fileName + ".session";
            api.core.saveSession( path, "true");
            System.out.println( "Session save successful (" + path + ")." );
        } catch (ClientApiException ex) {
            System.out.println( "Error saving session." );
            ex.printStackTrace();
        }
    }

    public boolean ascan(ClientApi api, String ZAP_URI_PORT) {
        try {
            System.out.println("Active scan starting...");
            api.ascan.scan(ZAP_URI_PORT, null, null, null, null, null);
            //kludge to see when scan is done - Currently am not sure how to work with the ApiRepsonse Object
            while (api.ascan.status(null).toString(0).contains("100") == false) {
                System.out.println("active scan progress: "	+ api.ascan.status(null).toString(0));
                try {
                    Thread.sleep(15000); //basically printing status every 15 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("progress: " + api.ascan.status(null).toString(0));
            return true;
        } catch (ClientApiException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean spider(ClientApi api, String ZAP_URI_PORT) {
        try{
            System.out.println("Spider scan starting...");
            Spider spider = new Spider( api );
            spider.scan( ZAP_URI_PORT, null, null, null, null);
            //kludge to see when spider has completed - currently am not sure how to use the ApiResponse Object
            while (spider.status(null).toString(0).contains("100") == false) {
                System.out.println("progress: "	+ spider.status(null).toString(0));
                try {
                    Thread.sleep(5000); //basically printing status every 5 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("progress: " + spider.status(null).toString(0));
            return true;
        } catch(ClientApiException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public String checkErrors(ClientApi api) {
        String errors = "";
        List<Alert> ignoreAlerts = new ArrayList<>(2);
        //ignoreAlerts.add(new Alert("Cookie set without HttpOnly flag", null, Risk.Low, Reliability.Warning, null, null) {});
        //ignoreAlerts.add(new Alert(null, null, Risk.Low, Reliability.Warning, null, null));
        //ignoreAlerts.add(new Alert(null, null, Risk.Informational, Reliability.Warning, null, null));
        try {
            System.out.println("Checking Alerts...");
            api.checkAlerts(ignoreAlerts, null);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            errors = ex.getMessage();
        }
        return errors;
    }

}
