package WhatsApp.MetaApi.WhatsAppServices;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.io.IOException;

@Service
public class SendWhatsApp {

    //Token
    @Value("${meta.token}")
    String token;
    //Phone to send the message


    @Value("${meta.idNumber}")
    String idNumber;

    public void sendWhatsAppMessage(String phoneNumber) {
        try {
            //URL
            URL url = new URL("https://graph.facebook.com/v15.0/" + idNumber + "/messages");
            //Open Connection
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("POST");
            //Use token
            httpConnection.setRequestProperty("Authorization", "Bearer " + token);
            //Send message as JSON
            httpConnection.setRequestProperty("Content-Type", "application/json; application/x-www-form-urlencoded; charset=UTF-8");
            httpConnection.setDoOutput(true);
            //Create message
            OutputStreamWriter writer = new OutputStreamWriter(httpConnection.getOutputStream());
            writer.write("{ "
                    + "\"messaging_product\": \"whatsapp\", "
                    + "\"to\": \"" + phoneNumber + "\", "
                    + "\"type\": \"template\", "
                    + "\"template\": "
                    + "  { \"name\": \"hello_world\", "
                    + "    \"language\": { \"code\": \"en_US\" } "
                    + "  } "
                    + "}");
            //Clean data
            writer.flush();
            //Close connection
            writer.close();
            httpConnection.getOutputStream().close();

            InputStream responseStream = httpConnection.getResponseCode() / 100 == 2
                    ? httpConnection.getInputStream()
                    : httpConnection.getErrorStream();
            Scanner s = new Scanner(responseStream).useDelimiter("\\A");
            String respuesta = s.hasNext() ? s.next() : "";

            System.out.println(respuesta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
