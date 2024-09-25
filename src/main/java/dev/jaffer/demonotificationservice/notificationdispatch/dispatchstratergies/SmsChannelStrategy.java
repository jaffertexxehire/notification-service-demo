package dev.jaffer.demonotificationservice.notificationdispatch.dispatchstratergies;

import dev.jaffer.demonotificationservice.notificationdispatch.DispatchPayload;
import org.springframework.stereotype.Component;

@Component
public class SmsChannelStrategy implements ChannelStrategy {

    @Override
    public void dispatchToChannel(DispatchPayload dispatchPayload) {

    }
}
