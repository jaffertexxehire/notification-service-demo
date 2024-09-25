package dev.jaffer.demonotificationservice.notificationdispatch.dispatchstratergies;

import dev.jaffer.demonotificationservice.notificationdispatch.DispatchPayload;

public interface ChannelStrategy {

    void dispatchToChannel(DispatchPayload dispatchPayload);
}
