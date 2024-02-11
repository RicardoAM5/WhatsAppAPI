package WhatsApp.MetaApi.WhatsAppServices;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Service
public class SendWhatsApp {

    // Meta token retrieved from configuration
    @Value("${meta.token}")
    String token;

    // WhatsApp ID number retrieved from configuration
    @Value("${meta.idNumber}")
    String idNumber;

    // Method to send a WhatsApp message
    public void sendWhatsAppMessage(String phoneNumber) {
        try {
            // Construct URL for WhatsApp message sending
            URL url = new URL("https://graph.facebook.com/v15.0/" + idNumber + "/messages");
            // Open connection
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("POST");
            // Set authorization header with token
            httpConnection.setRequestProperty("Authorization", "Bearer " + token);
            // Set content type and allow output
            httpConnection.setRequestProperty("Content-Type", "application/json");
            httpConnection.setDoOutput(true);

            // Construct the JSON message to be sent
            String message = "{ "
                    + "\"messaging_product\": \"whatsapp\", "
                    + "\"to\": \"" + phoneNumber + "\", "
                    + "\"type\": \"template\", "
                    + "\"template\": { \"name\": \"hello_world\", \"language\": { \"code\": \"en_US\" } } "
                    + "}";

            // Write the message to the connection output stream
            try (OutputStreamWriter writer = new OutputStreamWriter(httpConnection.getOutputStream())) {
                writer.write(message);
            }

            // Read response from the connection
            InputStream responseStream = (httpConnection.getResponseCode() / 100 == 2)
                    ? httpConnection.getInputStream()
                    : httpConnection.getErrorStream();

            // Process the response
            String response = new Scanner(responseStream, "UTF-8").useDelimiter("\\A").next();
            System.out.println(response);
        } catch (IOException e) {
            // Handle any I/O exceptions
            e.printStackTrace();
        }
    }
}
