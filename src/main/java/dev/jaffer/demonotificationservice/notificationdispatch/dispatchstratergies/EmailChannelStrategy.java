package dev.jaffer.demonotificationservice.notificationdispatch.dispatchstratergies;

import dev.jaffer.demonotificationservice.notificationdispatch.DispatchPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailChannelStrategy implements ChannelStrategy {


    private EmailVendorTransactional emailVendorTransactional;

    public EmailChannelStrategy(EmailVendorTransactional emailVendorTransactional) {
        this.emailVendorTransactional = emailVendorTransactional;
    }
    @Override
    public void dispatchToChannel(DispatchPayload dispatchPayload) {
        emailVendorTransactional.sendEmail(dispatchPayload);
    }
}
