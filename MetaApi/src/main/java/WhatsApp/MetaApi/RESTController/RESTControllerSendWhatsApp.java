package WhatsApp.MetaApi.RESTController;

import WhatsApp.MetaApi.WhatsAppServices.ReceivesWhatsApp;
import WhatsApp.MetaApi.WhatsAppServices.SendWhatsApp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/whatsapp")
@CrossOrigin(origins = "*")
public class RESTControllerSendWhatsApp {

    private final SendWhatsApp sendWhatsApp;
    private final ReceivesWhatsApp receivesWhatsApp;

    public RESTControllerSendWhatsApp(SendWhatsApp sendWhatsApp, ReceivesWhatsApp receivesWhatsApp) {
        this.sendWhatsApp = sendWhatsApp;
        this.receivesWhatsApp = receivesWhatsApp;
    }

    @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody String phoneNumber) {
        sendWhatsApp.sendWhatsAppMessage(phoneNumber);
    }


    @RequestMapping(value = "/receivesMessage", method = {RequestMethod.GET, RequestMethod.POST})
    public void recibirMensaje(HttpServletRequest request, HttpServletResponse response) throws IOException {
        receivesWhatsApp.getMessages(request, response);
    }
}
