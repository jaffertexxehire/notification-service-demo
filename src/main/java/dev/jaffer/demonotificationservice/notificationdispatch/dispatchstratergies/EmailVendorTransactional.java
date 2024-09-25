package dev.jaffer.demonotificationservice.notificationdispatch.dispatchstratergies;

import dev.jaffer.demonotificationservice.notificationdispatch.DispatchPayload;

public interface EmailVendorTransactional {

        void sendEmail(DispatchPayload dispatchPayload);
}
