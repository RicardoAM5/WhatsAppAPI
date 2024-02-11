package WhatsApp.MetaApi.WhatsAppServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class ReceivesWhatsApp extends HttpServlet {

    public void getMessages(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Our token
        String token = "HolaMundo";
        // MetaReto
        String metaReto = request.getParameter("hub.challenge");
        // MetaToken
        String verificationToken = request.getParameter("hub.verify_token");

        // Compare token and return MetaReto
        if (token.equals(verificationToken)) {
            response.getWriter().print(metaReto);
            return;
        }

        // GetMessages
        int size = request.getContentLength();

        if (size > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
                String line;
                // Read request input stream line by line
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }

            // Convert the received message to string
            String message = stringBuilder.toString();
            System.out.println(message);
        }
    }
}
