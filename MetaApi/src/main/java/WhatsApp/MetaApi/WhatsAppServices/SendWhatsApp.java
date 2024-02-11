package WhatsApp.MetaApi.WhatsAppServices;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class SendWhatsApp {

    // Meta token retrieved from configuration
    @Value("${meta.token}")
    private String token;

    // WhatsApp ID number retrieved from configuration
    @Value("${meta.idNumber}")
    private String idNumber;

    // Method to send a WhatsApp message
    public void sendWhatsAppMessage(String phoneNumber) {
        try {
            // Construct the URL for sending WhatsApp message
            String urlStr = "https://graph.facebook.com/v15.0/" + idNumber + "/messages";
            URL url = new URL(urlStr);

            // Open connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Set request method to POST
            connection.setRequestMethod("POST");
            // Set authorization header with token
            connection.setRequestProperty("Authorization", "Bearer " + token);
            // Set content type
            connection.setRequestProperty("Content-Type", "application/json");
            // Allow output
            connection.setDoOutput(true);

            // Construct the JSON message to be sent
            String message = "{ "
                    + "\"messaging_product\": \"whatsapp\", "
                    + "\"to\": \"" + phoneNumber + "\", "
                    + "\"type\": \"template\", "
                    + "\"template\": { \"name\": \"hello_world\", \"language\": { \"code\": \"en_US\" } } "
                    + "}";

            // Write the message to the connection output stream
            try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
                writer.write(message);
            }

            // Get the response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // If response is OK (200), read the response message
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    // Print success message and response body
                    System.out.println("Message sent successfully!");
                    System.out.println("Response: " + response.toString());
                }
            } else {
                // If response code is not OK, print error message
                System.err.println("Failed to send message! Response code: " + responseCode);
            }

        } catch (IOException e) {
            // Catch any I/O exceptions and print stack trace
            e.printStackTrace();
        }
    }
}
