package WhatsApp.MetaApi.RESTController;

import WhatsApp.MetaApi.WhatsAppServices.SendWhatsApp;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/whatsapp")
@CrossOrigin(origins = "*")
public class RESTControllerSendWhatsApp {

    private final SendWhatsApp sendWhatsApp;

    public RESTControllerSendWhatsApp(SendWhatsApp sendWhatsApp) {
        this.sendWhatsApp = sendWhatsApp;
    }

    @PostMapping("/sendMessage/")
    public void sendMessage(@RequestBody  String phoneNumber) {
        sendWhatsApp.sendWhatsAppMessage(phoneNumber);
    }

}
