package dev.jaffer.demonotificationservice.notificationdispatch.dispatchstratergies;

import dev.jaffer.demonotificationservice.notificationdispatch.DispatchPayload;

public interface EmailVendorBulk {

    void sendBulkEmail(DispatchPayload dispatchPayload);
}
