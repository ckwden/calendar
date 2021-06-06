package publicholidays.model.twilio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Implements the Messenger interface
 */
public class MessengerImpl implements Messenger {

    private final String sid;
    private final String token;
    private final String numberTo;
    private final String numberFrom;
    private final String url;

    public MessengerImpl(String sid, String token, String numberTo, String numberFrom) {
        this.sid = sid;
        this.token = token;
        this.numberTo = numberTo;
        this.numberFrom = numberFrom;
        this.url = "https://api.twilio.com/2010-04-01/";
    }

    @Override
    public void sendReport(String report) {
        String body = report.replaceAll(" ", "%20");
        BufferedReader reader;
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(this.url + "Accounts/" + this.sid + "/Messages.json?to=" + this.numberTo +
                    "&from=" + this.numberFrom + "&body=" + body);
            System.out.println(url.toString());
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setRequestProperty("Accept", "application/json");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // https://www.baeldung.com/java-http-url-connection
            String auth = this.sid + ":" + this.token;
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
            String authHeader = "Basic " + new String(encodedAuth);
            http.setRequestProperty("Authorization", authHeader);

            String toSend = "To=" + this.numberTo.replace("+", "%2B")
                    + "&From=" + this.numberFrom.replace("+", "%2B")
                    + "&Body=" + body;
            byte[] encodedToSend = toSend.getBytes(StandardCharsets.UTF_8);
            OutputStream os = http.getOutputStream();
            os.write(encodedToSend);

            String line;
            int status = http.getResponseCode();
            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(http.getErrorStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
            }
            while((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            http.disconnect();
        } catch (MalformedURLException e) {
            System.out.println("Invalid URL");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
